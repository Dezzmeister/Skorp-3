Dezzy

deftex: darkbricks,assets/raycast/textures/darkbricks.png,512,512
deftex: cartoonstones,assets/raycast/textures/cartoonstones.png,512,512
deftex: bars,assets/raycast/textures/bars.png,16,16

mapwidth: 10
mapdepth: 10

sector main:
	defpts:
		pt: 0,0
		pt: 0,10
		pt: 10,10
		pt: 10,0
	enddefpts
	
	floorheight: 0
	ceilheight: 1.25
	
	wall: 0,0,10,0
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

sector secondary:
	defpts:
		pt: 0,0
		pt: -10,0
		pt: -10,10
		pt: 0,10
	enddefpts
	
	wall: 0,0,-10,0
		settex: darkbricks
		tile: 8,1
	endwall
	
	wall: -10,0,-10,10
		settex: darkbricks
		tile: 8,1
	endwall
	
	wall: -10,10,0,10
		settex: cartoonstones
		tile: 8,1
	endwall
endsector

portal: 0,0,0,10,main,secondary