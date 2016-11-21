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
    			$('nav a').each(function(){
    				var x = this.href;
    				this.href += "?id=" + id;
				});
    			
    			// Update query string
    			if (typeof student !== 'undefined' && typeof student.curve != 'undefined') {
    				if (!hash.includes("a=" + student.curve.a)) {
    					hash += "&a=" + + student.curve.a;
    				}
    				if (!hash.includes("b=" + student.curve.b)) {
    					hash += "&b=" + + student.curve.b;
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
});
