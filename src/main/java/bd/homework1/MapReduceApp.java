package bd.homework1;

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.io.compress.SnappyCodec;

/** Main class of the application which allows input and output directories.
 *
 */

@Log4j
public class MapReduceApp {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            throw new RuntimeException("You should specify input and output folders.");
        }
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator", ",");

        //program name
        Job job = Job.getInstance(conf, "browser count");

        //class setup
        job.setJarByClass(MapReduceApp.class);
        job.setMapperClass(BrowserCountMapper.class);
        job.setReducerClass(BrowserCountReducer.class);
        job.setOutputKeyClass(Text.class);

        //value of output (browser count)
        job.setOutputValueClass(IntWritable.class);

        //type of output file - Sequence file
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        //output path setup
        Path outputDirectory = new Path(args[1]);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, outputDirectory);

        //enable output compression
        FileOutputFormat.setCompressOutput(job,true);

        //encoding output file
        FileOutputFormat.setOutputCompressorClass(job, SnappyCodec.class);
        job.waitForCompletion(true);

        // checking stats
        Counter counter = job.getCounters().findCounter(CounterType.MALFORMED);
        log.info("COUNTERS " + counter.getName() + ": " + counter.getValue());
    }
}
