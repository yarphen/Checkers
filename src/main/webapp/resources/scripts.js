$(document).ready(function(){
	var SIZE = 800;
	var bfs = $('#bfs');
	var dfs = $('#dfs');
	var game = $('.game');
	var column1 = $('#column1-submit');
	var column2 = $('#column2-submit');
	var column3 = $('#column3-submit');
	var column4 = $('#column4-submit');
	var black = $('#black-checker');
	var white = $('#white-checker');
	var eraser = $('#eraser');
	var mainchecker = $('#main-checker');
	column1.click(function(){
		$('#column1').removeClass('active-column');
		$('#column2').addClass('active-column');
		gameState.step=1;
		gameState.editor=1;
	});
	column2.click(function(){
		$('#column2').removeClass('active-column');
		$('#column3').addClass('active-column');
		gameState.step=2;
	});
	column3.click(function(){
		$('#column3').removeClass('active-column');
		$('#column4').addClass('active-column');
	});
	column4.click(function(){
		$('#column4').removeClass('active-column');
		$('#column1').addClass('active-column');
	});
	black.click(function(){
		gameState.editor=1;
		black.addClass('active-tool');
		white.removeClass('active-tool');
		eraser.removeClass('active-tool');
	});
	white.click(function(){
		gameState.editor=2;
		white.addClass('active-tool');
		black.removeClass('active-tool');
		eraser.removeClass('active-tool');
	});
	eraser.click(function(){
		gameState.editor=0;
		eraser.addClass('active-tool');
		white.removeClass('active-tool');
		black.removeClass('active-tool');
	});
	mainchecker.click(function(){
		gameState.editor=3-gameState.editor;
		switch(gameState.editor){
		case 1:mainchecker.addClass('black').removeClass('white');break;
		case 2:mainchecker.removeClass('black').addClass('white');break;
		}
	});
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
			success();
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
	var renderBoard = function(stage){
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
		var graphics = new PIXI.Graphics();
		var cellSize = SIZE/8;
		graphics.interactive=true;
		stage.addChild(graphics);
		var gameState = {};
		graphics.mousedown=function(eventData){
			var localPosition = graphics.toLocal(eventData.data.global);
			var x = Math.trunc(localPosition.x/cellSize);
			var y = Math.trunc(localPosition.y/cellSize);
			var cell = gameState.cells[x][y];
			if (!cell)return;
			switch(gameState.step){
			case 0:
				switch(gameState.editor){
				case 0:cell.white.alpha=0;cell.black.alpha=0;cell.state=0;break;
				case 1:cell.white.alpha=0;cell.black.alpha=1;cell.state=1;break;
				case 2:cell.white.alpha=1;cell.black.alpha=0;cell.state=2;break;
				}
				break;
			case 1:
				var checker = gameState.checker;
				if (cell.state==0){
					switch(gameState.editor){
					case 1:checker.white.alpha=0;checker.black.alpha=1;checker.color=1;break;
					case 2:checker.white.alpha=1;checker.black.alpha=0;checker.color=2;break;
					}
					checker.white.x = x*cellSize;
					checker.black.x = x*cellSize;
					checker.white.y = y*cellSize;
					checker.black.y = y*cellSize;
				}
				break;
			case 2:
			}
			
		};
		gameState.cells = [];
		gameState.checker = {};
		gameState.checker.black = generateChecker(0,0,cellSize,true,true);
		gameState.checker.white = generateChecker(0,0,cellSize,false,true);
		stage.addChild(gameState.checker.black);
		stage.addChild(gameState.checker.white);
		gameState.checker.x=0;
		gameState.checker.y=0;
		gameState.checker.color = 1;
		gameState.target = {};
		gameState.target.x=0;
		gameState.target.y=0;
		gameState.editor=1;
		gameState.step=0;
		gameState.algorithm='BFS';
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
		return gameState
	}
	var gameStage = new GameStage();
	var gameState = renderBoard(gameStage.stage);
	gameStage.draw();
});