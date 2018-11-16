class View
{
	constructor(model)
	{
		this.model = model;
		this.canvas = document.getElementById("myCanvas");
		this.turtle = new Image();
		this.turtle.src = "images/turtle.png";
		this.background = new Image();
		this.background.src = "images/background.png";
	}

	update()
	{
		let ctx = this.canvas.getContext("2d");
		ctx.clearRect(0, 0, 1000, 500);

		ctx.drawImage(background.image, 0, 0);

		for(let i = 0; i < this.model.sprites.length; i++)
		{
			let sprite = this.model.sprites[i];
			ctx.drawImage(sprite.image, sprite.x, sprite.y);
		}
	}
}
