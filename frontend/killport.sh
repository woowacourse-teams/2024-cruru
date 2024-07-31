PORT=$1

if [ -z "$PORT" ]; then
  echo "Usage: $0 <port>"
  exit 1
fi

PID=$(lsof -t -i:$PORT)

if [ -z "$PID" ]; then
  echo "No process is using port $PORT"
else
  kill -9 $PID
  echo "Process $PID using port $PORT has been terminated"
fi
