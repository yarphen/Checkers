$(document).ready(function(){
	var SIZE = 450;
	var PAUSE = 0.25;
	var fps = 30;
	var game = $('.game');
	var column1 = $('#column1-submit');
	var column2 = $('#column2-submit');
	var column3 = $('#column3-submit');
	var column4 = $('#column4-submit');
	var black = $('#black-checker');
	var white = $('#white-checker');
	var eraser = $('#eraser');
	var mainchecker = $('#main-checker');
	var bfs = $('#choose-bfs');
	var dfs = $('#choose-dfs');
	var duration = $('#duration');
	duration.change(function(){
		theGameState.duration = duration.val();
	});
	column1.click(function(){
		if (theGameState.allowNext){
			$('#column1').removeClass('active-column');
			$('#column2').addClass('active-column');
			theGameState.step=1;
			theGameState.editor=1;
			theGameState.allowNext = false;
		}
	});
	column2.click(function(){
		if (theGameState.allowNext){
			$('#column2').removeClass('active-column');
			$('#column3').addClass('active-column');
			theGameState.step=2;	
			theGameState.allowNext = false;
		}
	});
	column3.click(function(){
		if (theGameState.allowNext){
			$('#column3').removeClass('active-column');
			$('#column4').addClass('active-column');
			theGameState.step=3;
			theGameState.allowNext = true;
		}
	});
	column4.click(function(){
		if (theGameState.allowNext){
			$('#column4').removeClass('active-column');
			var data = createAction({
				id: theGameState.id,
				algorithm: theGameState.algorithm,
			});
			$.ajax({
				url: 'solve/'+theGameState.id,
				data: JSON.stringify(data),
				method: 'POST',
				success: function(currentWay){
					var startState;
					if (currentWay.states.length!=0){
						startState = currentWay.states[currentWay.states.length-1];
					}
					var onfinish = function(){
						if (startState)theGameState.applyGameState(startState);
						theGameState.checker.hide();
						theGameState.target.hide();
						theGameState.step = 0;
						$('#column1').addClass('active-column');
						mainchecker.addClass('black').removeClass('white');
						black.click();
					};
					var showWay = function(way, finish){
						if (way.states.length==0){
							finish();
							return;
						}
						var gameState = way.states.pop();
						theGameState.applyGameState(gameState, function(){
							setTimeout(function(){
								showWay(way,finish);
							}, theGameState.duration*PAUSE);
						});
					};
					console.log(currentWay);
					if (startState){
						showWay(currentWay, onfinish);
					}else{
						alert('NO WAY EXISTS!')
						onfinish();
					}
				},
				contentType: 'application/json'
			});
		}
	});
	black.click(function(){
		theGameState.editor=1;
		black.addClass('active-tool');
		white.removeClass('active-tool');
		eraser.removeClass('active-tool');
	});
	white.click(function(){
		theGameState.editor=2;
		white.addClass('active-tool');
		black.removeClass('active-tool');
		eraser.removeClass('active-tool');
	});
	eraser.click(function(){
		theGameState.editor=0;
		eraser.addClass('active-tool');
		white.removeClass('active-tool');
		black.removeClass('active-tool');
	});
	mainchecker.click(function(){
		theGameState.editor=3-theGameState.editor;
		if (theGameState.allowNext){
			theGameState.checker.update();
		}
		switch(theGameState.editor){
		case 1:
			mainchecker.addClass('black').removeClass('white');
			break;
		case 2:
			mainchecker.removeClass('black').addClass('white');
			break;
		}
	});
	bfs.click(function(){
		theGameState.algorithm='BFS';
		bfs.addClass('active-tool');
		dfs.removeClass('active-tool');
	});
	dfs.click(function(){
		theGameState.algorithm='DFS';
		dfs.addClass('active-tool');
		bfs.removeClass('active-tool');
	});
	function createAction(sample){
		var action = {
				id: '',
				x: -1,
				y: -1,
				checkerState: -1,
				algorithm: ''
		};
		if (sample.id!==undefined)action.id = sample.id;
		if (sample.x!==undefined)action.x = sample.x;
		if (sample.y!==undefined)action.y = sample.y;
		if (sample.checkerState!==undefined)action.checkerState = sample.checkerState;
		if (sample.algorithm!==undefined)action.algorithm = sample.algorithm;
		return action;
	};
	function GameStage(){
		var renderer = new PIXI.CanvasRenderer(SIZE, SIZE);
		game.append(renderer.view);
		var stage = new PIXI.Stage;
		var draw = function () {
			renderer.render(stage);
			requestAnimationFrame(draw);
		}
		this.draw = draw;
		this.stage = stage;
		this.renderer = renderer;
	};
	var plainMove = function(sprite, from, to, duration, success, steps, step){
		if (step === undefined){
			step = 0;
		}
		if (step>steps){
			if (success)success();
		}else{
			var coef1 = (step+0.0)/steps;
			var coef2 = 1 - coef1;
			sprite.x = coef2*from.x + coef1*to.x;
			sprite.y = coef2*from.y + coef1*to.y;
			setTimeout(function(){
				plainMove(sprite, from, to, duration, success, steps, step+1);
			}, duration/steps);
		}
	};
	var scaleOut = function(sprite, delay, q, steps){
		sprite.width*=q;
		sprite.height*=q;
		if (steps>0){
			setTimeout(function(){
				scaleOut(sprite, delay, q, steps-1);
			}, delay);
		}
	};
	var createGame = function(stage, id){
		var cellSize = SIZE/8;
		var graphics = new PIXI.Graphics();
		stage.addChild(graphics);
		var generateChecker = function(i,j,cellSize,color,special){
			var graphics = new PIXI.Graphics();
			if (color){
				graphics.lineStyle(4, 0xFFFFFF, 1);
				graphics.beginFill(0x000000, 1);
			}else{
				graphics.lineStyle(4, 0x000000, 1);
				graphics.beginFill(0xFFFFFF, 1);
			}
			graphics.drawCircle(i*cellSize+cellSize/2, j*cellSize+cellSize/2,cellSize/2);
			if (special){
				graphics.drawCircle(i*cellSize+cellSize/2, j*cellSize+cellSize/2,cellSize/4);
			}
			graphics.alpha=0;
			return graphics;
		};
		var generateImage = function(x,y,size,url,alpha){
			var object = PIXI.Sprite
			.fromImage(url);
			object.width = size;
			object.height = size;
			object.x = x;
			object.y = y;
			object.alpha = alpha;
			return object;
		};
		var applyCellState = function(state, cell){
			switch(state){
			case 0:cell.state=0;cell.white.alpha=0;cell.black.alpha=0;break;
			case 1:cell.state=1;cell.white.alpha=0;cell.black.alpha=1;break;
			case 2:cell.state=2;cell.white.alpha=1;cell.black.alpha=0;break;
			}
		};
		var gameState = {
				id: id,
				cells: [],
				checker: {
					black: generateChecker(0,0,cellSize,true,true),
					white: generateChecker(0,0,cellSize,false,true),
					x: 0,
					y: 1,
					color: 1,
					set: function(x,y,finish){
						var start = {};
						start.x = this.black.x;
						start.y = this.black.y;
						var end = {};
						end.x = x*cellSize;
						end.y = y*cellSize;
						var visible, invisible;
						switch(this.color){
						case 1:visible=this.black;invisible=this.white;break;
						case 2:invisible=this.black;visible=this.white;break;
						}
						invisible.x = end.x;
						invisible.y = end.y;
						plainMove(visible,
								start,
								end,
								gameState.duration,
								finish, 
								gameState.duration*fps/1000);
						this.x = x;
						this.y = y;
					},
					update: function(){
						var checker = gameState.checker;
						switch(gameState.editor){
						case 1:
							checker.white.alpha=0;checker.black.alpha=1;checker.color=1;
							break;
						case 2:
							checker.white.alpha=1;checker.black.alpha=0;checker.color=2;
							break;
						}
						var data = createAction({
							id: id,
							x: this.x,
							y: this.y,
							checkerState: gameState.editor
						});
						$.ajax({
							url: 'active/'+gameState.id,
							data: JSON.stringify(data),
							method: 'POST',
							success: function(o){
								gameState.allowNext = true;
							},
							contentType: 'application/json'
						});
					},
					init: function(){
						this.set(-1,-1);
					},
					hide: function(){
						var checker = gameState.checker;
						checker.white.alpha=0;
						checker.black.alpha=0;
					}
				},
				target: {
					x: 1,
					y: 0,
					sprite:  generateImage(-1,-1,cellSize,'resources/target.gif',0),
					hide: function(){
						this.sprite.alpha = 0;
					}
				},
				editor: 1,
				step: 0,
				duration: duration.val(),
				algorithm: 'BFS',
				allowNext: true,
				applyGameState: function(state, finish){
					applyCellState(state.mainChecker.color?1:2, this.checker);
					var loc = state.mainChecker.location;
					this.checker.set(loc.x, loc.y, finish);
					for(var i=0; i<8; i++){
						for(var j=0; j<8; j++){
							if (this.cells[i][j]){
								applyCellState(state.board.cells[i][j], this.cells[i][j]);
							}
						}
					}
				}
		};
		gameState.checker.init();
		graphics.interactive=true;
		graphics.mousedown=function(eventData){
			var localPosition = graphics.toLocal(eventData.data.global);
			var x = Math.trunc(localPosition.x/cellSize);
			var y = Math.trunc(localPosition.y/cellSize);
			var cell = gameState.cells[x][y];
			var checker = gameState.checker;
			if (!cell)return;
			switch(gameState.step){
			case 0:
				var data = createAction({
					id: id,
					x: x,
					y: y,
					checkerState: gameState.editor
				});
				$.ajax({
					url: 'board/'+gameState.id,
					data: JSON.stringify(data),
					method: 'POST',
					success: function(o){
						applyCellState(gameState.editor,cell);
					},
					contentType: 'application/json'
				});
				break;
			case 1:
				if (cell.state==0){
					checker.set(x,y);
					checker.update();
				}
				break;
			case 2:
				var target = gameState.target;
				if (cell.state==0 && (x !=checker.x || y != checker.y)){
					var data = createAction({
						id: id,
						x: x,
						y: y
					});
					$.ajax({
						url: 'target/'+gameState.id,
						data: JSON.stringify(data),
						method: 'POST',
						success: function(o){
							target.sprite.alpha = 1;
							target.sprite.x = x*cellSize;
							target.sprite.y = y*cellSize;
							target.x = x;
							target.y = y;
							gameState.allowNext = true;
						},
						contentType: 'application/json'
					});
				}
				break;
			}

		};
		for(var i=0; i<8; i++){
			gameState.cells[i]=[];
			for(var j=0; j<8; j++){
				if ((i+j)%2==1){
					graphics.beginFill(0x000000, 1);
					var cell = {};
					cell.black = generateChecker(i,j,cellSize,true,false);
					cell.white = generateChecker(i,j,cellSize,false,false);
					cell.state = 0;
					gameState.cells[i][j]=cell;
					stage.addChild(cell.black);
					stage.addChild(cell.white);
				}else{
					graphics.beginFill(0xFFFFFF, 1);
				}
				graphics.drawRect(i*cellSize, j*cellSize, cellSize, cellSize);
			}
		}
		stage.addChild(gameState.checker.black);
		stage.addChild(gameState.checker.white);
		stage.addChild(gameState.target.sprite);
		return gameState;
	}
	var gameStage = new GameStage();
	var theGameState;
	$.ajax({
		url: 'game',
		data: '',
		method: 'PUT',
		success: function(data){
			theGameState=createGame(gameStage.stage,data.id);
		}
	});
	gameStage.draw();
	$(window).bind('beforeunload', function(e){
		$.ajax({
			url: 'game/'+theGameState.id,
			data: '',
			async: false,
			method: 'DELETE'
		});
	});
});