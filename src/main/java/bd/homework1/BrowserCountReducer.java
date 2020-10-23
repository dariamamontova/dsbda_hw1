package bd.homework1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer - application which used for summarize values from mapper.
 */

public class BrowserCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        //iterate over the received values
        while (values.iterator().hasNext()) {
            sum += values.iterator().next().get();
        }

        //writing in context browser name and count
        context.write(key, new IntWritable(sum));
    }
}
