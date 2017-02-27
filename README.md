# ToDoListApp
A simple to-do list app in Android, building mainly to learn more about Android myself, but the intent is to turn it into a fully functioning app in the future.

# TODO - 2/26/17
+ Add a second activity for adding tasks.
+ Add a "priority" setting for each task, either "low", "medium", or "high". 
+ Order the views in the MainActivity by priority.
+ When you click on a task name, it takes you to another screen that shows you more data about the task.
+ Change the colors of the tasks to reflect the priority of them.

# UPDATES - 2/26/17
+ The "task" object was actually completely unnecessary. Decided to go with an SQLite database as that will likelly be more extensible for the future (if I want to add more attributes to each task)
+ Database works, but only kept the app to one activity for now. Plans to add a second activity are underway.


# BUGS - 2/26/17
+ Possible bug: List does not update unless you click the button numerous times, even though onClick is called each time the button is clicked. Theoretically, the list should update every time. Possible that there is something wrong in the RecyclerView implementation.
