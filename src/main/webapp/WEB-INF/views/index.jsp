<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/pixi.js/4.0.0/pixi.min.js">
	
</script>
<script type="text/javascript" src="resources/scripts.js">
	
</script>
<link rel="stylesheet" href="resources/styles.css" />
<meta charset="utf-8" content="text/html">
</head>
<body>
	<div class="container">
		<div class="content">
			<div class="title">
				<h2>Checkers</h2>
			</div>
			<div class="game-container">
				<div class="game"></div>
				<div class="column-container">
					<div id="column1" class="column active-column">
						<div id="black-checker" class="circle-tool black active-tool" title="Press to select black checker"></div>
						<div id="white-checker" class="circle-tool white" title="Press to select white checker"></div>
						<div id="eraser" class="circle-tool grey-and-red eraser-tool" title="Press to select eraser">X</div>
						<div id="column1-submit" class="column-footer" title="NEXT STEP">>></div>
					</div>
					<div id="column2" class="column">
						<div id="main-checker" class="circle-tool black active-tool" title="This is main checker. Press to change it's colour and place it on the board"></div>
						<div id="column2-submit" class="column-footer" title="NEXT STEP">>></div>
					</div>
					<div id="column3" class="column">
						<div id="target" class="circle-tool grey-and-red active-tool" title="This is target. Place it on the board">+</div>
						<div id="column3-submit" class="column-footer" title="NEXT STEP">>></div>
					</div>
					<div id="column4" class="column">
						<div id="choose-bfs"
							class="circle-tool grey-and-red text-tool active-tool" title="Press to select BFS algorithm">BFS</div>
						<div id="choose-dfs" class="circle-tool grey-and-red text-tool" title="Press to select DFS algorithm">DFS</div>
						<div id="column4-submit" class="column-footer text-column-footer" title="PRESS TO FIND THE WAY" title="Press to start">GO!</div>
					</div>
				</div>
			</div>
			<div class="duration-spinner">
				<span>Duration of step, milliseconds: </span><input id="duration" min="100" max="4000" type="number" value="700" step="100"/>
			</div>
		</div>
	</div>
</body>
</html>

