/**
 * Naturally, this would be a Single Page App. However we want to play around with jQuery here.
 */
var app = {};

// event listeners - through delegation they don't have to be initialized on DOM ready
$(document).on("click", ".btn-primary", function() {
    $(".l-hidden").removeClass("l-hidden");
});

$(document).on("click", "[class^=navbar] a", function() {
    // Set active element
    $(".active").removeClass("active");
    $(this).parent().addClass("active");

    // Perform our mini-routing
    var path = $(this).attr("href").split("#")[1];

    if(path === "") {
        app.api.fetchBiggestCities();
    }
    else if(path === "all") {
        app.api.fetchAllCities();
    } else if(path === "big") {
        app.api.fetchBiggestCities();
    } else if(path === "sweden") {
        app.api.fetchSwedishCities();
    }
});

/**
 * API for all queries
 */
app.api = {};

// use this for benchmarking
app.api.time = 0;

/**
 * Generic AJAX data reader, will create a deferred and returns a promise.
 *
 * @param name The name of this reader instance
 * @param url The URL that will be queried
 * @returns {*} A promise
 */
app.api.Reader = function(name, url) {
    var d = new $.Deferred();

    $.ajax({
        url: url,
        dataType: 'json',
        success: function(data) {
            // trigger notification - not really needed here, but generally useful
            d.notify(data.length);
            d.resolve(data);
        }, error: function() {
            d.reject();
        }
    });

    var promise = d.promise();

    // tis gets triggered on d.notify()
    promise.progress(function(data) {
        console.log("Received " + data + " items");
    });

    return promise;
};

app.api.populateTable = function(data) {
    // We should really use templating here, but this works for now
    var row = $(".city-row:first");
    $(".city-row").remove();

    var rows = [];

    for(var i in data) {
        var currentRow = $(row).clone();
        $(currentRow).find(".city-counter").text(i);
        $(currentRow).find(".city-name").text(data[i].accentCity);
        $(currentRow).find(".city-country").text(data[i].country);
        $(currentRow).find(".city-population").text(data[i].population);
        $(".table tbody").append(currentRow);
    }
};


app.api.fetchAllCities = function() {
    // Benchmark this since there is a lot going on here
    app.api.time = new Date().getTime();

	console.log("Getting all cities");

    $(".jumbotron").hide();

    var cities = new app.api.Reader("all", "/spring-web-mongo/readAll");

    cities.done(function(data) {
        // Create rows for the received data - we would need to paginate here, for now just slice
        app.api.populateTable(data.slice(0, 1024));

        console.log("All cities received, finished in " + (new Date().getTime() - app.api.time) + "ms.");

        $(".l-hidden").removeClass("l-hidden");
    });

    cities.fail(function() {
        console.log("Could not read all cities");
    });
};

app.api.fetchBiggestCities = function() {
    // Benchmark this since there is a lot going on here
    app.api.time = new Date().getTime();

    console.log("Getting biggest cities");

    $(".jumbotron").hide();

    var cities = new app.api.Reader("big", "/spring-web-mongo/readBiggest");

    cities.done(function(data) {
        // Create rows for the received data
        app.api.populateTable(data);

        console.log("All biggest cities received, finished in " + (new Date().getTime() - app.api.time) + "ms.");

        $(".l-hidden").removeClass("l-hidden");
    });

    cities.fail(function() {
        console.log("Could not read biggest cities");
    });
};

app.api.fetchSwedishCities = function() {
    // Benchmark this since there is a lot going on here
    app.api.time = new Date().getTime();

    console.log("Getting Swedish cities");

    $(".jumbotron").hide();

    var cities = new app.api.Reader("sweden", "/spring-web-mongo/readCities/se");

    cities.done(function(data) {
        // Create rows for the received data
        app.api.populateTable(data);

        console.log("All swedish cities received, finished in " + (new Date().getTime() - app.api.time) + "ms.");

        $(".l-hidden").removeClass("l-hidden");
    });

    cities.fail(function() {
        console.log("Could not read Swedish cities");
    });
};


$(document).ready(function() {
    console.log("Document ready");
});