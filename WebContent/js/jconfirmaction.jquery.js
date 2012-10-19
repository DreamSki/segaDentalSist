/*
 * jQuery Plugin : jConfirmAction
 * 
 * by Hidayat Sagita
 * http://www.webstuffshare.com
 * Licensed Under GPL version 2 license.
 *
 */
(function($){

	jQuery.fn.jConfirmAction = function (options) {
		
		// Some jConfirmAction options (limited to customize language) :
		// question : a text for your question.
		// yesAnswer : a text for Yes answer.
		// cancelAnswer : a text for Cancel/No answer.
		var theOptions = jQuery.extend ({
			title : "Hola",
			question: "Are You Sure ?",
			yesAnswer: "Yes",
			cancelAnswer: "Cancel"
		}, options);
		
		return this.each (function () {
			
			$(this).bind('click', function(e) {

				e.preventDefault();
				thisHref	= $(this).attr('href');
				name = $(this).attr("name");
				var questionAux = theOptions.question;
				var question = questionAux.replace("{0}", name);
				if($(this).next('.question').length <= 0)
					$(this).after('<div class="question"><div class="dialogo">'+ 
							'<div class="titleDial">'+theOptions.title+'</div></br></br>' + question 
							 +'<br/><br/> <span class="yes">'+theOptions.yesAnswer+
							'</span><span class="cancel">'+theOptions.cancelAnswer+
							'</span></div></div>');
				
				$(this).next('.question').animate({opacity: 1}, 300);
				
				$('.yes').bind('click', function(){
					window.location = thisHref;
				});
		
				$('.cancel').bind('click', function(){
					$(this).parents('.question').fadeOut(300, function() {
						$(this).remove();
					});
				});
				
			});
			
		});
	};
	
})(jQuery);