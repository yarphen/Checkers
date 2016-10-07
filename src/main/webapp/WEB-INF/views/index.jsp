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
					<div class="column active-column">
						<div id="black-checker" class="circle-tool black"></div>
						<div id="white-checker" class="circle-tool white"></div>
						<div id="eraser" class="circle-tool grey-and-red">×</div>
						<div id="column1-submit" class="column-footer">>></div>
					</div>
					<div class="column">
						<div id="main-checker" class="circle-tool black"></div>
						<div id="column2-submit" class="column-footer">>></div>
					</div>
					<div class="column">
						<div id="target"class="circle-tool grey-and-red">+</div>
						<div id="column3-submit" class="column-footer">>></div>
					</div>
					<div class="column">
						<div id="choose-bfs" class="circle-tool grey-and-red text-tool">BFS</div>
						<div id="choose-dfs" class="circle-tool grey-and-red text-tool">DFS</div>
						<div id="column4-submit" class="column-footer">>></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

