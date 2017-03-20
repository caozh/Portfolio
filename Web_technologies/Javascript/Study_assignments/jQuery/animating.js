$(function() {
   /** image functions 
    ******************************************/ 
	$("#show").click(function() {
		$("#image11").show("normal");
	});
	$("#hide").click(function() {
		$("#image11").hide("normal");
	});
	$("#toggle").click(function() {
		$("#image11").toggle("slow");
	});
	/** movement functions 
    ******************************************/ 
	$("#toLeft").click(function() {
		$("#image22").animate({ left: "-=50px" }, 500, "swing");
	});
	$("#toRight").click(function() {
		$("#image22").animate({ left: "+=50px" }, 500, "swing");
	});
	$("#toTop").click(function() {
		$("#image22").animate({ top: "-=50px" }, 500, "swing");
	});
	$("#toBottom").click(function() {
		$("#image22").animate({ top: "+=50px" }, 500, "swing");
	});
	/** slide functions 
    ******************************************/ 
	$("#slideup").click(function() {
		$("#image33").slideUp("normal");
	});
	$("#slidedown").click(function() {
		$("#image33").slideDown("normal");
	});
	$("#toggle-slide").click(function() {
		$("#image33").slideToggle("slow");
	});
	/** fade functions 
    ******************************************/ 
	$("#fadein").click(function() {
		$("#image44").fadeIn("normal");
	});
	$("#fadeout").click(function() {
		$("#image44").fadeOut("normal");
	});
	$("#fadeto3").click(function() {
		$("#image44").fadeTo("slow", 0.3);
	});
	$("#fadeup").click(function() {
		$("#image44").fadeTo("slow", 1.0);
	});
	/** Accordion: Open on mouseover functions 
    ******************************************/ 
	$( "#accordion" ).accordion();

	// create the image rotator
	setInterval("rotateImages()", 3000);
	
	
});

	function rotateImages() {
            var oCurPhoto = $('#photoShow div.current');
            var oNxtPhoto = oCurPhoto.next();
            if (oNxtPhoto.length == 0)
                oNxtPhoto = $('#photoShow div:first');

            oCurPhoto.removeClass('current').addClass('previous');

            oNxtPhoto.css({ opacity: 0.0 }).addClass('current').animate({ opacity: 1.0 }, 1000,
                function() {
                    oCurPhoto.removeClass('previous');
                });
				};
				
	/** Accordion: Open on mouseover functions 
    ******************************************/ 
	/*$(function() {
		//$( "#accordion" ).accordion({
		//	event: "mouseover"
		//});
	});*/