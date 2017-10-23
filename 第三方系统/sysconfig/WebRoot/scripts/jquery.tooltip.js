(function(jQuery){
	jQuery.fn.tooltip=function(){
			var x=10;
			var y=15;
			this.mouseover(function(p){
					this.myTitle = this.title;
					this.title = '';      
					var tooltip = "<div id='tooltip'>"+this.myTitle+"<\/div>";  
					jQuery("body").append(tooltip);
					jQuery("#tooltip").css({
						"top":(p.pageY+y)+"px",
						"left":(p.pageX+y)+"px"
					}).show("fast");
			}).mouseout(function(){
					this.title = this.myTitle;
					jQuery("#tooltip").remove();
			}).mousemove(function(p){
					jQuery("#tooltip")
					.css({
						"top":(p.pageY+y)+"px",
						"left":(p.pageX+y)+"px"
					});
			})
		};
})(jQuery);