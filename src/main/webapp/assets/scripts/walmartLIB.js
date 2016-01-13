; (function() {
    'use strict';

    window.walmart = {
        version: '1.0',

        postJSON: function (url, options) {
            var xhr = walmart.createRequest('POST', url, {async: true});

            xhr.onload = function () {
                if (xhr.status != 200) {
                    options.error(xhr, "status: " + xhr.status);
                } else if (options.success && angular.isFunction(options.success)) {
                    var response = angular.fromJson(this.responseText);

                    options.success(response);
                }
            };

            xhr.onerror = function () {
                if (options.error && angular.isFunction(options.error)) {
                    options.error(xhr, xhr.status);
                }
            };

            xhr.setRequestHeader('Accept', 'application/json');

            if (options.data) {
                var string = options.isRoutes ? "{\"routes\": " + JSON.stringify( options.data ) + " }" : JSON.stringify( options.data );

                xhr.setRequestHeader('Content-type', 'application/json');
                xhr.setRequestHeader('Content-Length', string.length);

                xhr.send(string);
            } else {
                xhr.setRequestHeader('Content-type', ( options.contentType ? options.contentType : 'application/x-www-form-urlencoded' ) + "; charset=UTF-8" );

                xhr.send();
            }
        },

        getJSON: function (url, options) {
            walmart.requestAJAX({
                method: 'OPTIONS',
                url: url,
                async: true,

                onLoad: function (xhr, responseText) {
                    if (xhr.status != 200) {
                        options.error(xhr, "status: " + xhr.status);
                    } else if (options.success && angular.isFunction(options.success)) {
                        var response = angular.fromJson(responseText);

                        options.success(response);
                    }
                }
            });
        },

        loadJSON: function(url) {
            return walmart.syncRequestJSON('GET', url);
        },

        deleteJSON: function(url) {
            return walmart.syncRequestJSON('DELETE', url);
        },

        syncRequestJSON: function(method, url, options) {
            var result = {};

            if ( !options) {
                options = {};
            }

            walmart.requestAJAX({
                method: method,
                url: url,
                async: false,
                contentType: options.contentType,

                onLoad: function (xhr, responseText) {
                    if (xhr.status != 200) {
                        result.error = {
                            xhr: xhr,
                            message: "status: " + xhr.status,
                            status: xhr.status
                        };
                    } else {
                        try {
                            result = angular.fromJson(responseText);
                        } catch (error) {
                            result.error = {
                                xhr: xhr,
                                message: "Invalid return: [" + responseText + "]",
                                status: xhr.status
                            }
                        }
                    }
                },

                onError: function (xhr) {
                    result.error = {
                        xhr: xhr,
                        message: "status: " + xhr.status,
                        status: xhr.status
                    };
                }
            });

            return result;
        },

        requestAJAX: function(settings ) {
            var jqXHR = walmart.createRequest(settings.method, settings.url, {async: settings.async});

            jqXHR.onload = function () {
                settings.onLoad(jqXHR, this.responseText);
            };
            jqXHR.onerror = function () {
                settings.onError(jqXHR);
            };

            jqXHR.setRequestHeader('Accept', 'application/json');
            jqXHR.setRequestHeader('Content-type', 'application/json');

            jqXHR.send();
        },

        createRequest: function (method, url, options) {
            var jqXHR = new XMLHttpRequest();

            if ("withCredentials" in jqXHR) {
                var async = options && options.async == false ? false : true;

                jqXHR.open(method, url, async);
            } else if (typeof XDomainRequest != "undefined") { // IE8 & IE9
                jqXHR = new XDomainRequest();
                jqXHR.open(method, url);
            } else {
                jqXHR = null;
            }

            return jqXHR;
        },
        
        formatNumber: function(num) {
            var str = num.toString().replace("$", ""), parts = false, output = [], i = 1, formatted = null;
            if(str.indexOf(".") > 0) {
                parts = str.split(".");
                str = parts[0];
            }
            str = str.split("").reverse();
            for(var j = 0, len = str.length; j < len; j++) {
                if(str[j] != ".") {
                    output.push(str[j]);
                    if(i%3 == 0 && j < (len - 1)) {
                        //output.push(",");
                    }
                    i++;
                }
            }
            formatted = output.reverse().join("");
            return( formatted + ((parts) ? "." + parts[1].substr(0, 2) : ""));
        }
    };
}());
