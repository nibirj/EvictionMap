##My solution

I have maid two classes that work together. First class *EvictionMap* contains 2 methods put and get.  
When initializing *EvictionMap* it is possible to give *"long"* value in seconds,   
this value means how long map will store values. If value is not given, then it calls another constructor  
that will give duration time Long.MAX_VALUE.  
For thread safety I use *ConcurrentHashMap*, *Timer*, *TimerTasks*.  
Put method stores key and value in map. In put method I start timer, when it is not running.  
This method controls, if there are any already running timer tasks, that should be canceled,  
so no needed key would get removed.  
In MyTimerTask I give map, key and timer. Map is needed for deleting value by key, after a certain time.  
Timer is needed to close him, when no keys and values are in map. Key is needed to find value, that needs to be deleted.
Get method, returns value by key. 
