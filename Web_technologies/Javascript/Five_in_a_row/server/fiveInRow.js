exports.fiveInRow = function(){
	// player status
	var STAT_NOT_READY = 0;		// just login
	var STAT_READY  = 1;		// ready for playing
	var STAT_PLAYING  = 2;	    // playing
	// strone color
	var BLACK_STONE = 1;		// black
	var WHITE_STONE = 2;		// white
    
	var myPORT = 8080;
	var players = [];			// players in connection
    var playerID = [];          // to look up player ID
	var board = [];//           //Game board
	var num_client = 0;         //clients manager
	var dim = 19;               // dimension 19 * 19
	var io;	
	
	//load user customize port 
	this.setConfig = function(port){
		myPORT = port;
	}
	
	//First initialize the game  board
	var InitChessData = function(){
		for(var i = 0; i < dim; i++){
			board[i] = [];
			for(var j = 0; j < dim; j++){
				board[i][j] = 0;
			}
		}
	}
	
	//Initialize the game  board
	var ResetCheseData = function(){
		for(var i =0 ; i < dim; i++){
			for(var j = 0; j < dim; j++){
				board[i][j] = 0;
			}
		}
	}
	
	// start server
	this.serverStart = function(){
		//first initialize the game boaard
		InitChessData();
		
		// socket connection
		io = require('socket.io').listen(myPORT);
		io.sockets.on('connection', function (socket) {
			console.log('a user connected');
			//Disconnect
			socket.on("disconnect", OnClose);
			
			//Login
			socket.on("login", OnLogin);

			//Ready
			socket.on("ready", OnReady);
			
			//Message
			socket.on('message', OnMessage);
			
			//place stone
			socket.on("placeStone", OnPlaceStone);
		});
		console.log('server is started, port: ' + myPORT);
	}
	
	//load player information
	var player_info = function(sid){
		return {
			"id" : players[sid].socket.id,
			"nickname" : players[sid].nickname,
			"status" : players[sid].status
		}
	}
	
	// login
	var OnLogin = function(data){
		var login_status = 0;
		var i = this.id;
        console.log('onLogin i: ' + i);
		if(num_client < 2){
			var player = {
				socket   : this,
				nickname : data.nickname,
				status   : STAT_NOT_READY
			};
			//server record the player info
			players[i] = player;
            playerID.push(i);
			num_client++;
			//login succeed
			this.emit("login", {
				"login_status"  : 1,
				"info" : player_info(i)
			});
            io.sockets.emit("message", {
                nickname : data.nickname,
                text : " enter the game"
            });
		}
		else{
			//login failed
			this.emit("login", {"login_status" : 0});
		}
	}
		
    
	//close
	var OnClose = function(data){
		var sid = this.id;
		if(!players[sid]) return ;
		//inform player
		io.sockets.emit("close", {
			"nickname" : players[sid].nickname
		});
		//if thay are in gaming, we initialize the staying player's game
        if(players[sid].status == STAT_PLAYING){
            players[playerID[0]].status = STAT_NOT_READY;
            players[playerID[1]].status = STAT_NOT_READY;
        }
		//delete the player
        players.splice(sid, 1);
        for (var i = 0; i < playerID.length; i++){
            console.log('Before: playerID['+i+']: '+ playerID[i]);    
        }
        for (var i = 0; i < playerID.length; i++){
            if (playerID[i] == sid){
                console.log('playerID[] Delete '+sid);
                playerID.splice(i, 1);
            }
        }
        for (var i = 0; i < playerID.length; i++){
            console.log('After: playerID['+i+']: '+ playerID[i]);    
            console.log('After: players[sid].status: '+ players[playerID[i]].status);   
        }
		num_client--;
	}
    
	// ready 
	var OnReady = function(data){
		var i = this.id;
        console.log('i: '+i+' OnReady: players[sid].status: '+ players[i].status);   
		if (players[i] && players[i].status != STAT_PLAYING){
            //If already be ready, it cancel the ready and set to not ready satus
			var status = 1 - players[i].status;
			players[i].status = status;
			
			//send back to clients to tell who is ready
			io.sockets.emit("ready", {
				"id"      : i,
				"nickname": players[i].nickname,
				"status"  : status
			});			
            for (var i = 0; i < playerID.length; i++){
                console.log('Ready: i: '+i+' player id: '+playerID[i]+ ' status: '+ players[playerID[i]].status);
            }
			// start if two players are all ready
            if (players[playerID[0]]&&
                players[playerID[1]] && 
                players[playerID[0]].status == STAT_READY && players[playerID[1]].status == STAT_READY){
                console.log('OnReady: Game started');
                //tell clients to start game
                players[playerID[0]].status = STAT_PLAYING;
                players[playerID[1]].status = STAT_PLAYING;
                players[playerID[0]].socket.emit("start", {
                    "color" : BLACK_STONE,
                    "allowPlace" : true
                });
                players[playerID[1]].socket.emit("start", {
                    "color" : WHITE_STONE,
                    "allowPlace" : false
                });
            }	
		}
	}
	
	// place stone
	var OnPlaceStone = function(data){
		if( players[playerID[0]] && 
            players[playerID[1]] && 
            players[playerID[0]].status == STAT_PLAYING && 
            players[playerID[1]].status == STAT_PLAYING &&
			checkValidChess(data.x, data.y) == true){
            console.log('OnPlaceStone');     
			data.id = this.id;
			board[data.x][data.y] = data.color;
            //inform players the new placed stone
			for(var i = 0; i < 2; i++){
				players[playerID[i]].socket.emit("placeStone", data);
			}
			//check if win
			if(checkGameOver(data.x, data.y) == true){
				var first  = playerID[0];
				var second = playerID[1];
				var winner  = (data.id == first ? first : second);
				var loser  = (data.id == second ? first : second);
				players[first].status = STAT_NOT_READY;
				players[second].status = STAT_NOT_READY;
				ResetCheseData();
                console.log('winner: '+ data.color);   
				players[winner].socket.emit("winner", "");	
				players[loser].socket.emit("loser", "");	
                io.sockets.emit("message", {
                    nickname : players[winner].nickname,
                    text : " win!"
                });				
			}
		}
	}
	
	//check if the move is valid
	var checkValidChess = function(x, y){
		if(board[x][y] == 1){
			return false;
		}
		return true;
	}
	
	//check if the game has a winner
	var checkGameOver = function(x, y){
		var n;
		var cur = board[x][y];
		//horizontal
		n = 0;
		var startX = (x - 4) < 0 ? 0 : x - 4;
		var endX   = (x + 4) > 18 ? 18 : x + 4;
		for(var i = startX; i <= endX; i++){
			if(board[i][y] == cur){
				n++;
			}else{
				n = 0;
			}
			if(n >= 5) return true;
		}
		//vertical
		n = 0;
		var startY = (y - 4) < 0 ? 0 : x - 4;
		var endY   = (y + 4) > 18 ? 18 : y + 4;	
		for(var i = startY; i <= endY; i++){
			if(board[x][i] == cur){
				n++;
			}else{
				n = 0;
			}
			if(n >= 5) return true;
		}
		
		//left skew
		n = 0;
		var min = x < y ? (x - 4 < 0 ? x : 4) : (y - 4 < 0 ? y : 4);
		var max = x > y ? (x + 4 > 18 ? 18 - x : 4) : (y + 4 > 18 ? 18 - y : 4); 
		var p1x = x - min;
		var p1y = y - min;
		var p2x = x + max;
		var p2y = y + max;
		for(var i = p1x, j = p1y; i <= p2x, j <= p2y; i++, j++){
			if(board[i][j] == cur){
				n++;
			}else{
				n = 0;
			}
			if(n >= 5) return true;
		}
		
		//right skew
		n = 0;
		var min = (x + 4 > 18 ? 18 - x : 4) < (y - 4 < 0 ? y : 4) ? 
				  (x + 4 > 18 ? 18 - x : 4) : (y - 4 < 0 ? y : 4);
		var max = (x - 4 < 0 ? x : 4) < (y + 4 > 18 ? 18 - y : 4) ?
				  (x - 4 < 0 ? x : 4) : (y + 4 > 18 ? 18 - y : 4);
		var p1x = x + min;
		var p1y = y - min;
		var p2x = x - max;
		var p2y = y + max;
		for(var i = p1x, j = p1y; i >= p2x; i--, j++){
			if(board[i][j] == cur){
				n++;
			}else{
				n = 0;
			}
			if(n >= 5) return true;
		}
		
		return false;
	}
	
	//send message
	var OnMessage = function (data) {
		var sid = this.id;
		if(!players[sid]) return;
		var cli = players[sid];
		var msg = {
			id : cli.socket.id,
			nickname : cli.nickname,
			text : data.text
		};
        if(data.text){
            io.sockets.emit("message", msg);
        }
	}
}