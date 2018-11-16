class Sprite
{
	constructor(x, y, image_url, update_method, onclick_method)
	{
		this.x = x;
		this.y = y;
		this.image = new Image();
		this.image.src = image_url;
		this.update = update_method;
		this.onclick = onclick_method;
	}

	set_destination(x, y)`	`
	{
		this.dest_x = x;
		this.dest_y = y;
	}

	ignore_click(x, y)
	{
	}

	move(dx, dy)
	{
		this.dest_x = this.x + dx;
		this.dest_y = this.y + dy;
	}

	go_toward_destination()
	{
		if(this.dest_x === undefined)
			return;

		if(this.x < this.dest_x)
			this.x++;
		else if(this.x > this.dest_x)
			this.x--;
		if(this.y < this.dest_y)
			this.y++;
		else if(this.y > this.dest_y)
			this.y--;
	}

	sit_still()
	{
	}


}
class Mario extends Sprite
{
	constructor(x, y, image_url)
	{
		super(x, y, update, onClick)
		w = 60;
		h = 95;
	}

	update()
	{

		vert_vel += 10;
	}

	onClick()
	{

	}
}

class Brick extends Sprite
{
	constructor()
	{

	}
}
