Dezzy

deftex: darkbricks,assets/raycast/textures/darkbricks.png,512,512
tex: cartoonbricks,assets/raycast/textures/cartoonbricks.png,512,512
tex: bars,assets/raycast/textures/bars.png,16,16

mapwidth: 10
mapdepth: 10

sector:
	floorheight: 0
	ceilheight: 1.25
	
	wall: 0,0,10,0
		tile: 8,1
	endwall
	
	wall: 0,0,0,10
		tile: 8,1
	endwall
	
	wall: 10,0,10,10
		tile: 8,1
	endwall
	
	wall: 0,10,10,10
		tile: 8,1
	endwall
	
	wall: 0,1,1,1
		settex: cartoonstones
	endwall
	
	wall: 1,1,4,4
		settex: cartoonstones
	endwall
	
	wall: 4,4,4,7
		settex: cartoonstones
	endwall
	
	wall: 4,7,4,8
		settex: bars
	endwall
	
	wall: 4,8,4,9.25
		settex: cartoonstones
	endwall
endsector