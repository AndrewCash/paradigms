class Model
{
	constructor()
	{
		this.sprites = [];
		this.sprites.push(new Mario(200, 100));
		this.turtle = new Sprite(50, 50, "images/turtle.png", Sprite.prototype.go_toward_destination, Sprite.prototype.set_destination);
		this.sprites.push(this.turtle);
	}

	update()
	{
		for(let i = 0; i < this.sprites.length; i++)
		{
			this.sprites[i].update();
		}
	}

	onclick(x, y)
	{
		for(let i = 0; i < this.sprites.length; i++)
		{
			this.sprites[i].onclick(x, y);
		}
	}

	move(dx, dy)
	{
		this.turtle.move(dx, dy);
	}
}
