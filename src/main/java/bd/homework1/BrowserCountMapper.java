package bd.homework1;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper used for parse logs and search browser type.
 */
public class BrowserCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        //parse the string
        UserAgent userAgent = UserAgent.parseUserAgentString(line);

        //counter for logs with unknown browsers (user agents)
        if (userAgent.getBrowser() == Browser.UNKNOWN) {
            context.getCounter(CounterType.MALFORMED).increment(1);
        } else {
            //setting browser name
            word.set(userAgent.getBrowser().getName());
            context.write(word, one);
        }
    }
}
