Dezzy

import texturedefs stdtex: levels/standardtextures.tex

mapwidth: 10
mapdepth: 10

walltemplate cartoonwall:
	tile: 8,1
	settex: stdtex.cartoonstones
endtemplate

sector main:
	defpts:
		pt: 0,0
		pt: 0,10
		pt: 10,10
		pt: 10,0
	enddefpts
	
	wallheight: 1.3
	sectorheight: 0.15
	
	wall: 0,0,10,0
		tile: 8,1
	endwall
	
	wall: 10,0,10,10
		tile: 8,1
	endwall
	
	wall: 0,10,10,10,cartoonwall
	
	wall: 0,1,1,1
		settex: stdtex.cartoonstones
	endwall
	
	wall: 1,1,4,4
		settex: stdtex.cartoonstones
	endwall
	
	wall: 4,4,4,7
		settex: stdtex.cartoonstones
	endwall
	
	wall: 4,7,4,8
		settex: stdtex.bars
	endwall
	
	wall: 4,8,4,9.25
		settex: stdtex.cartoonstones
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
		settex: stdtex.darkbricks
		tile: 8,1
	endwall
	
	wall: -10,0,-10,10
		settex: stdtex.darkbricks
		tile: 8,1
	endwall
	
	wall: -10,10,0,10
		settex: stdtex.cartoonstones
		tile: 8,1
	endwall
endsector

portal: 0,0,0,10,main,secondary