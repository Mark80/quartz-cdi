package triggerCreator;

import java.io.Serializable;

import org.quartz.JobExecutionException;

import fire.Fire;

public interface JobExecutable extends Serializable {

    public String getName();

    public void execute(Fire fire) throws JobExecutionException;

}
