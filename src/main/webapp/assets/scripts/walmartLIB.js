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
                var string = options.isRoutes ? JSON.stringify("{\"routes\": " + options.data + " }") : JSON.stringify( options.data );

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

        uploadPhoto: function(settings) {
            var xhr = walmart.createRequest('POST', settings.url, {async: settings.async});

            xhr.onload = function () {
                if (settings.success && angular.isFunction(settings.success)) {
                    var response = angular.fromJson(this.responseText);

                    settings.success(response.photoUrl);
                }
            };

            xhr.onerror = function () {
                settings.error(xhr, xhr.status);
            };

            var formData = new FormData();

            formData.append('photo', settings.files[0]);

            xhr.send(formData);
        }
    };
}());
