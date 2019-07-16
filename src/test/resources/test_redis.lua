local key1 = KEYS[1]
local key2 = KEYS[2]
local argv1 = ARGV[1]
local argv2 = ARGV[2]

redis.log(redis.LOG_WARNING, "-------"   .. key1)
redis.log(redis.LOG_WARNING, "-------"   .. key2)
redis.log(redis.LOG_WARNING, "-------"   .. argv1)
redis.log(redis.LOG_WARNING, "-------"   .. argv2)
--redis.log(redis.LOG_WARNING, "-------key1" ,tostrng(ARGV[1]))

redis.call("set", key1, argv1)
redis.call("set", key2, argv2)
