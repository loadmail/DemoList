
	
		
		message中存有handler对象
		 Message obtain(Handler h)
		 
		 
		 主线程中有Looper
		 looper中有queue;
		 
		 
		 Looper.loop()取queue中的message,交给handler
		 loop()有个死循环,不断取出message,交给handler
		  msg.target.dispatchMessage(msg); --> (runnable = msg.callback)
		  handleMessage(msg);|| handleCallback(msg);
		  
		 
		 
		 message中有target handler对象
		  Message obtain(Handler h);
		 
		 
		 发送消息
		 handler.sendMessage(msg)
		 最终调用的是 queue.enqueueMessage(Message msg, long when) 是什么效果呢?
		 
		 把message放到单链表中合适的位置
		 
		 
		 
		 
		 //todo  message.obtain()
		 
		消息列表的维护是通过单链表的形式来维护的
		把消息中的第一条数据取出来,然后把第二条变成第一条
		 mPool是消息队列中的第一个消息  需要画出堆栈图分析
		  public static Message obtain() {
        synchronized (sPoolSync) {
			//线程池里的消息sPool不为空
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }
		 
		
		 
		 
	  
		主线程中new Handler的时候就拿到了  线程  的Looper和MessageQueue  mQueue  mLooper
		   public Handler(Callback callback, boolean async) {
       

        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
			// 抛异常 looper对象为空
                "Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
        mCallback = callback;
        mAsynchronous = async;
    }
		 
		 
		 
		  Looper.myLooper();  -->  return sThreadLocal.get();  获得looper
		  
		  
		  ThreadLocal :
		  多线程的并发相关  功能:实现线程内的单例
		  
		  子线程1 -->Session.getUser()-->Session.getUser()  是一个对象
		  
		  子线程2 -->Session.getUser()-->Session.getUser()---> Session.getUser()   
		  
		  线程1和线程2的user不同,单独线程中的user是同一对象
		  
		  
		  有get就有set,set方法就在Looper.prepare();
		  
		  在哪个线程中调用,就把new looper存到这个线程中
 private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
		// 这里new Looper,在哪个线程new,就存到哪个线程中
		//把looper存到当前线程中
    }
		  
		  
		  主线程的looper是什么时候放进去的?
		   public static void prepareMainLooper() {
        prepare(false);  //调用
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
		   }
		 
		   private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);  //队列
        mThread = Thread.currentThread();
    }
		 
		 在ActivityThread.java中有程序启动的入口main方法,
		 这个方法是在主线程上的
		 Looper.prepareMainLooper();
		 通过prepare方法构造一个含有queue的Looper,并把它存到主线程中
		 sThreadLocal.set(new Looper());  //主线程
		 
		  public static void loop() {
			  for(;;){
				   Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }
			  }
		  }
		 
		 
		 主线程入口类: ActivityThread.java 中的main()
		 main(){
			 
			 Looper.prepareMainLooper(); //创建looper和messageQueue并把looper存到mainThread
			 Looper.loop();  //主线程不点击也不结束之谜,是个死循环,主线程不会销毁和退出 
			 
		 }
		 main方法的作用:准备轮询器,不断的取消息,结束.
		 所有的交互都是通过获得消息来实现的.
		 
		 
		 在主线程中创建handler对象
		 Handler handler = new Handler();
handler的构造中拿到所在线程的looper 和对应的 mQueue 
mLooper = Looper.myLooper();	
   return sThreadLocal.get();  
   
  mQueue = mLooper.mQueue;
  
	以上是拿到的 mLooper = sThreadLocal.get(),但是在什么时候存进去的呢?	 
	在Looper.prepare方法中:
	sThreadLocal.set(new Looper());
	
	步骤1 new Handler();
	步骤2 handler.sendMessage();
	
	mQueue.enqueueMessage():
	把handler存到Message中
	把message存到mQueue中  用单链表结构把messge放到合适的位置
		 
	 private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
		 
        msg.target = this;  //handler message由目标handler处理
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        return queue.enqueueMessage(msg, uptimeMillis);  
    } 
		 
		 
		 
		  public static void loop() {
			  
			    for (;;) {
					 Message msg = queue.next();
				}
				 msg.target.dispatchMessage(msg);  //handler.dispatchMessage(msg);
			  
		  } 
		 
		 handler.dispatchMessage(msg);--> 
		 
		 haveCallback?  -->handleCallback(msg): handleMessage(msg);
		 
	
		  msg.target.dispatchMessage(msg); --> (runnable = msg.callback)
		  handleMessage(msg);|| handleCallback(msg);
		 
****************************************************************************************		 
		 步骤1 new handler;
		 获取线程中的queue和looper;
		 android进程启动的时候looper就开始轮询消息.
		 
		 直接 new Handler();
		 new Handler(Callback);
		 
		 new Handler(); ---> getLooper and getQueue; 
		 -->  mLooper = Looper.myLooper();
		 -->    return sThreadLocal.get();
		 
		 looper和queue从哪里来的?
		 -->  sThreadLocal.set();
		 -->  looper.prepare();
		 为什么mainThread中没有looper.prepare()?
		 looper是如何轮询消息的?
		 
		  主线程入口类: ActivityThread.java 中的main()
		  --> 
		  main()
		  {
			 Looper.prepareMainLooper(); -->prepare();//创建looper和messageQueue并把looper存到mainThread
			 Looper.loop();  //主线程不点击也不结束之谜,是个死循环,主线程不会销毁和退出 
		 }
		 --->
		  Looper.loop();-->
		  public static void loop() {
			  
			    for (;;) {
					 Message msg = queue.next(); 如果没有消息,Binder阻塞,不返回msg对象,有消息就返回msg
				}
				 msg.target.dispatchMessage(msg);  -->handler.dispatchMessage(msg);
			  
		  } 
		  
		  --->触发handleMessage();
		   handler.dispatchMessage(msg);--> 
		 
		 haveCallback?  -->handleCallback(msg): handleMessage(msg);
		 
	
		  msg.target.dispatchMessage(msg); --> (runnable = msg.callback)
		  handleMessage(msg);|| handleCallback(msg);
		 
		 
		 
		 
		 
		 步骤2 : msg = Message.obtain();  获取消息对象msg
		 得到消息对象 msg = Message.obtain();
		 这里有一个问题,mPool在哪里被初始化,或者被谁初始化?
		 sPool是一个单链表,因为message对象中还包含着另一个Message next;
		 也表示第一个消息对象
		 链表不为空,就取第一个,为空,就new Message();
		 
		 Message.obtain(); --> (msg == null)? --->new Message();  :  msg;
		 
		  public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
               
                return m;
            }
        }
        return new Message();
    }
		 
		步骤3:hanler.sendMessage(msg); 
		其实就是把msg加入到queue中
		
	hanler.sendMessage(msg); -->	sendMessageAtTime(Message msg, long uptimeMillis) 
	-->	  enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis)
	---> 
	msg绑定handler
	把消息放到queue中   queue.enqueueMessage(msg, uptimeMillis);
		 
********************************************************************************************		 
		 
		 
		 
		
	   