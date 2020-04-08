package core.objectManagers;

import core.loading.LoadingTask;
import core.sout.LogType;
import core.sout.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TaskLoader {

    private final List<LoadingTask> tasksToLoad = Collections.synchronizedList(new ArrayList<>());
    private final List<String> taskMessages = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addLoadingTask(String message, LoadingTask loadingTask) {
        synchronized (tasksToLoad) {
            tasksToLoad.add(loadingTask);
        }
        synchronized (taskMessages) {
            taskMessages.add(message);
        }
    }

    public void loadTasks() {
        Iterator<LoadingTask> iterator = tasksToLoad.iterator();
        while (iterator.hasNext()) {
            LoadingTask task = iterator.next();
            Logger.PRINT(LogType.INFO, "Task Loading Message:" + taskMessages.get(tasksToLoad.indexOf(task)));
            task.onLoad();
            iterator.remove();
        }
    }

    public LoadingTask getTaskToLoad() {
        if (tasksToLoad.size() > 0) {
            synchronized (tasksToLoad) {
                Iterator<LoadingTask> iterator = tasksToLoad.iterator();
                while (iterator.hasNext()) {
                    LoadingTask taskToLoad = iterator.next();
                    iterator.remove();
                    return taskToLoad;
                }
            }
        }
        return null;
    }

    public int getNumOfTasks() {
        return tasksToLoad.size();
    }

    public void cleanUp() {
        synchronized (tasksToLoad) {
            tasksToLoad.clear();
        }
    }

}
