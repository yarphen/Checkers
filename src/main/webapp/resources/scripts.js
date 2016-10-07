$(document).ready(function(){
	var bfs = $('#bfs');
	var dfs = $('#dfs');
	var game = $('.game');
	function GameStage(){
		var renderer = new PIXI.CanvasRenderer(game.width(), game.width());
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
	var gameStage = new GameStage();
});