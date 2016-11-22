var generate;

$(document).ready(function() {

	var hash = window.location.search;

	console.log(hash);

	if (hash[0] === "?") {
		hash = hash.substr(1);
	}

	var items = hash.split("&");

	for (var i = 0; i < items.length; i += 1) {
		var item = items[i].split("=");
		var name = item[0];
		var value = item[1];

		console.log(name + " : " + value);

		if (item.length !== 2 || !name || !value || /[^a-z]/.test(name) || /[^a-zA-Z0-9]/.test(value)) {
			continue;
		}

		if (name !== "id") {
			continue;
		}

		var url = "/api/students/" + value;
		var id = value;

		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : url,
			success : function(student) {
				console.log("SUCCESS: ", student);
				// Update the navigation links
				$('nav a').each(function() {
					var x = this.href;
					this.href += "?id=" + id;
				});

				// Update query string
				if (typeof student !== 'undefined' && typeof student.curve != 'undefined') {
					if (!hash.includes("a=" + student.curve.a)) {
						hash += "&a=" + student.curve.a;
					}
					if (!hash.includes("b=" + student.curve.b)) {
						hash += "&b=" + student.curve.b;
					}
					if (hash[0] === "&") {
						hash = hash.substr(1);
					}
					hash = "?" + hash;
					if (window.location.search != hash) {
						window.location.search = hash;
					}
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}

	$('#coefficient-a').on('input propertychange paste', function() {
		console.log("coefficient a changed to " + $('#coefficient-a').val());
	});

	generate = function() {
		$(function() {
			var primes = [ 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179 ];
			for (a = -9; a < -4; a++) {
				$('#coefficient-a').val(a);
				for (b = Math.abs(a); b < 16; b++) {
					$('#coefficient-b').val(b);
					for (p in primes) {
						$('#coefficient-p').val(primes[p]);
						$.ec.curve = new $.ec.modk.ScalarMultiplication();
						$.ec.curve.update();
						var order = $.ec.curve.getSubgroupOrder();
						if ($.ec.curve.isPrime(order) && order > 60) {
							console.log("a =", a, "b =", b, "p =", primes[p], "order =", order);
						}
					}
				}
			}
		});
	}
});

(function($) {
	$.CO3326 = function() {
		var location = window.location.pathname;
		var html = location.substring(location.lastIndexOf("/") + 1);
		
		console.log(html);
		
		var modkMul = function() {
			var results = [];
			var primes = [ 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179 ];
			for (var a = -9; a < -4; a++) {
				$('#coefficient-a').val(a);
				for (var b = Math.abs(a); b < 16; b++) {
					$('#coefficient-b').val(b);
					for (var p in primes) {
						$('#coefficient-p').val(primes[p]);
						$.ec.curve = new $.ec.modk.ScalarMultiplication();
						$.ec.curve.update();
						var order = $.ec.curve.getSubgroupOrder();
						if ($.ec.curve.isPrime(order) && order > 60) {
							results.push({
									a: a,
									b: b,
									p: primes[p]
							});
							console.log("a =", a, "b =", b, "p =", primes[p], "order =", order);
						}
					}
				}
			}
			return results;
		};
		
		var modkAdd = function() {
			console.warn("Yet to be implemented:", html);
			// TODO implement
			return [];
		};

		var realsAdd = function() {
			console.warn("Yet to be implemented:", html);
			// TODO implement
			return [];
		};

		var realsMul = function() {
			console.warn("Yet to be implemented:", html);
			// TODO implement
			return [];
		};
		
		var generate = {
			"modk-add.html" : modkAdd,
			"modk-mul.html" : modkMul,
			"reals-add.html" : realsAdd,
			"reals-mul.html" : realsMul
		}
		
		var cw = {
			generate: function() {
				var ret = [];
				$(function() {
					ret = generate[html]();
					console.log(ret);
				});
				return ret;
			}
		};
		
		return {
			generate: cw.generate
		};
	};	
})(jQuery);