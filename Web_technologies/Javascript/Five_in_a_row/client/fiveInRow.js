function fiveInRow_Client(host, port)
{
    var m_Host = host;
    var m_Port = port;
    var m_Events = [];
    var socket;
    var self = this;
    
    //event handler 
    var bindEvent = function()
    {
        for(var e in m_Events){
            socket.on(e, m_Events[e]);
        }
    }
    
    //setup the connection
    this.connect = function() {
        if(!("io" in window)){
            return false;
        }
        socket = io.connect('http://' + m_Host + ':' + m_Port);
        bindEvent();
        return true;
    }
    
     //Login
     this.login = function(nickname){
         socket.emit("login", {
             "nickname" : nickname
         });
     }

    //Send message
    this.sendMsg = function(data){
        socket.emit("message", {
            "text" : data
        });
    }
    
    //Ready
    this.ready = function(){
        socket.emit("ready", "");
    }
    
    //Place stone
    this.placeStone = function(color, x, y){
        socket.emit("placeStone", {
            "color" : color,
            "x" : x,
            "y" : y
        });
    }
    
    //event handler
    this.on = function(event, callback){
        m_Events[event] = callback;
        return self;
    }
}