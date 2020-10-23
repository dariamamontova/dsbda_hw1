import eu.bitwalker.useragentutils.UserAgent;
import bd.homework1.CounterType;
import bd.homework1.BrowserCountMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CountersTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

    private final String testMalformedIP = "lkv dkvmlskdvm";
    private final String testIP = "3.167.108.238 - - [12/Jul/2020:14:27:11 +0100] \"GET /mysite/photo9/927-3.jpg HTTP/1.1\" 200 40028 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\"\n";

    @Before
    public void setUp() {
        BrowserCountMapper mapper = new BrowserCountMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    @Test
    public void testMapperCounterOne() throws IOException  {
        mapDriver
                .withInput(new LongWritable(), new Text(testMalformedIP))
                .runTest();
        assertEquals("Expected 1 counter increment", 1, mapDriver.getCounters()
                .findCounter(CounterType.MALFORMED).getValue());
    }

    @Test
    public void testMapperCounterZero() throws IOException {
        UserAgent userAgent = UserAgent.parseUserAgentString(testIP);
        mapDriver
                .withInput(new LongWritable(), new Text(testIP))
                .withOutput(new Text(userAgent.getBrowser().getName()), new IntWritable(1))
                .runTest();
        assertEquals("Expected 1 counter increment", 0, mapDriver.getCounters()
                .findCounter(CounterType.MALFORMED).getValue());
    }

    @Test
    public void testMapperCounters() throws IOException {
        UserAgent userAgent = UserAgent.parseUserAgentString(testIP);
        mapDriver
                .withInput(new LongWritable(), new Text(testIP))
                .withInput(new LongWritable(), new Text(testMalformedIP))
                .withInput(new LongWritable(), new Text(testMalformedIP))
                .withOutput(new Text(userAgent.getBrowser().getName()), new IntWritable(1))
                .runTest();

        assertEquals("Expected 2 counter increment", 2, mapDriver.getCounters()
                .findCounter(CounterType.MALFORMED).getValue());
    }
}
