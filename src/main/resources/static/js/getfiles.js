$(document).ready(
		function() {

			var url = window.location;

			/*
			 * Get Request for the data retrieve.
			 */
			$("#GetFiles").click(function(event) {
				event.preventDefault();
				getData();
			});

			function getData() {
				$.ajax({
					type : "GET",
					url : url + "/get-all-files-uploaded",
					success : function(data) {
						$("#result").html("");
						$.each(data, function(i, v) {
							$("#result").append(
									'<a href=' + v + '>' + v + '</a> </br>')
						});
					},
					error : function(e) {
						$("#result").html(e.responseText);
					}
				});
			}
		})