package api.task;

import api.ScriptContext;
import api.task.impl.Consumer;
import api.task.impl.Idler;

import java.util.ArrayList;

/**
 * Created by HP xw8400
 * Author: Jacob
 * Date: 1/2/2018.
 */
public class TaskManager
{
    private final ArrayList<ScriptTask> tasks = new ArrayList<>();
    private final ScriptContext context;
    private final ScriptTask defaultTask;
    private ScriptTask currentTask;

    public TaskManager(final ScriptContext context, final ScriptTask... tasks)
    {
        this.context = context;
        for (ScriptTask task : tasks)
        {
            addTask(task);
        }
        defaultTask = new Idler();
        currentTask = defaultTask;

        Consumer.ConsumptionType.HEAL.setIDs(context.getFighterProfile().getFoodID());
        Consumer.ConsumptionType.RESTORE.setIDs(3030, 3028, 3026, 3024);
    }

    /**
     * Gets the task that should be performed next
     *
     * @return current ScriptTask
     */
    public ScriptTask getIdealTask()
    {
        for (ScriptTask task: tasks)
        {
            if (task.canPerform())
            {
                currentTask = task;
                return task;
            }
        }

        currentTask = defaultTask;
        return defaultTask;
    }

    public void addTask(ScriptTask task)
    {
        task.setContext(context);
        tasks.add(task);
    }

    public ScriptTask getCurrentTask()
    {
        return currentTask;
    }

    public ScriptContext getContext()
    {
        return context;
    }

    public ArrayList<ScriptTask> getTaskPool()
    {
        return tasks;
    }

    public ScriptTask getDefaultTask()
    {
        return defaultTask;
    }
}