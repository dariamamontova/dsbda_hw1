import eu.bitwalker.useragentutils.UserAgent;
import bd.homework1.BrowserCountMapper;
import bd.homework1.BrowserCountReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Tests for map-reduce application/
 */

public class MapReduceTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    private ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    private MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

    private final String testIP = "3.167.108.238 - - [12/Jul/2020:14:27:11 +0100] \"GET /mysite/photo9/927-3.jpg HTTP/1.1\" 200 40028 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"\n";

    private UserAgent userAgent;

    //setup
    @Before
    public void setUp() {
        BrowserCountMapper mapper = new BrowserCountMapper();
        BrowserCountReducer reducer = new BrowserCountReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        userAgent = UserAgent.parseUserAgentString(testIP);
    }

    //test for mapper app - getting browser name and count =1
    @Test
    public void testMapper() throws IOException {
        mapDriver
                .withInput(new LongWritable(), new Text(testIP))
                .withOutput(new Text(userAgent.getBrowser().getName()), new IntWritable(1))
                .runTest();
    }

    //test for reducer - if we have 2 same user agents, it get value=2
    @Test
    public void testReducer() throws IOException {
        List<IntWritable> values = new ArrayList<>();
        values.add(new IntWritable(1));
        values.add(new IntWritable(1));
        reduceDriver
                .withInput(new Text(testIP), values)
                .withOutput(new Text(testIP), new IntWritable(2))
                .runTest();
    }

    //test for map-reduce
    @Test
    public void testMapReduce() throws IOException {
        mapReduceDriver
                .withInput(new LongWritable(), new Text(testIP))
                .withInput(new LongWritable(), new Text(testIP))
                .withInput(new LongWritable(), new Text(testIP))
                .withOutput(new Text(userAgent.getBrowser().getName()), new IntWritable(3))
                .runTest();
    }
}
