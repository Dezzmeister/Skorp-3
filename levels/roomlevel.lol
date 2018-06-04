Dezzy

import texturedefs rooms: levels/rooms.tex

mapwidth: 100
mapdepth: 100

walltemplate border:
	tile: 12.8,1
	settex: rooms.stdwall
endtemplate

walltemplate stairborder:
	tile: 1,1
	settex: rooms.stdwall
endtemplate

sector main:
	defpts:
		pt: 0,0
		pt: 0,20
		pt: 20,20
		pt: 20,0
	enddefpts
	
	wallheight: 1.25
	sectorheight: 0
	
	color: 16711680
	
	//Sector border walls
	wall: 0,0,0,20,border
	//wall: 0,20,20,20,border
	wall: 0,20,8,20
		tile: 6.4,1
		settex: rooms.stdwall
	endwall
	
	wall: 12,20,20,20
		tile: 6.4,1
		settex: rooms.stdwall
	endwall
	
	wall: 20,20,20,0,border
	wall: 20,0,0,0,border
	
	wall: 0,10,4,10
		tile: 2.56,1
		settex: rooms.stdwall
	endwall
	
	wall: 0,16,4,16
		tile: 2.56,1
		settex: rooms.stdwall
	endwall
	
	wall: 4,10,4,11.5
		settex: rooms.stdwindow
	endwall
	
	wall: 4,11.5,4,13
		settex: rooms.stdwall
	endwall

endsector

sector stair0:
	defpts:
		pt: 8,20
		pt: 12,20
		pt: 12,21.5
		pt: 8,21.5
	enddefpts
		
	wallheight: 1.25
	sectorheight: 0.15
	
	color: 65280
		
	wall: 8,20,8,21.5,stairborder
	wall: 12,20,12,21.5,stairborder
endsector

sector stair1:
	defpts:
		pt: 8,21.5
		pt: 12,21.5
		pt: 12,23
		pt: 8,23
	enddefpts
	
	wallheight: 1.25
	sectorheight: 0.3
	
	color: 255
	
	wall: 8,21.5,8,23,stairborder
	wall: 12,21.5,12,23,stairborder
endsector

sector stair2:
	defpts:
		pt: 8,23
		pt: 12,23
		pt: 12,24.5
		pt: 8,24.5
	enddefpts
	
	wallheight: 1.25
	sectorheight: 0.45
	
	color: 65535
	
	wall: 8,23,8,24.5,stairborder
	wall: 12,23,12,24.5,stairborder
endsector

sector stair3:
	defpts:
		pt: 8,24.5
		pt: 12,24.5
		pt: 12,26
		pt: 8,26
	enddefpts
	
	wallheight: 1.25
	sectorheight: 0.6
	
	color: 16711935
	
	wall: 8,24.5,8,26,stairborder
	wall: 12,24.5,12,26,stairborder
	
	wall: 8,26,12,26
		tile: 2.56,1
		settex: rooms.stdwall
	endwall
endsector

portal: 8,20,12,20,main,stair0
portal: 8,21.5,12,21.5,stair0,stair1
portal: 8,23,12,23,stair1,stair2
portal: 8,24.5,12,24.5,stair2,stair3