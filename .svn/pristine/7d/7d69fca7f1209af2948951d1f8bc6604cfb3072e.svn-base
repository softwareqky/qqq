/**
 * WebSocket相关的函数和对象。
 */

/** STOMP对象 */
var STOMP_CLIENT = null;

/** SockJS对象 */
var SOCKET = null;

/** SockJS 0.3.4的transport列表，包含websocket，需要服务器支持 */
var TRANSPORTS_WEBSOCKET = ['websocket', 'xdr-streaming', 'xhr-streaming', 'iframe-eventsource',
        'iframe-htmlfile', 'xdr-polling', 'xhr-polling', 'iframe-xhr-polling', 'jsonp-polling'];

/** SockJS 0.3.4的transport列表，不包含websocket，使用替代方案 */
var TRANSPORTS_NOWEBSOCKET = ['xdr-streaming', 'xhr-streaming', 'iframe-eventsource',
        'iframe-htmlfile', 'xdr-polling', 'xhr-polling', 'iframe-xhr-polling', 'jsonp-polling'];

/** SockJS 1.3.0的transport列表，包含websocket，需要服务器支持 */
var NEW_TRANSPORTS_WEBSOCKET = ['websocket', 'xdr-streaming', 'xhr-streaming', 'eventsource',
        'iframe-eventsource', 'htmlfile', 'iframe-htmlfile', 'xdr-polling', 'xhr-polling',
        'iframe-xhr-polling', 'jsonp-polling'];

/** SockJS 1.3.0的transport列表，不包含websocket，使用替代方案 */
var NEW_TRANSPORTS_NOWEBSOCKET = ['xdr-streaming', 'xhr-streaming', 'eventsource',
        'iframe-eventsource', 'htmlfile', 'iframe-htmlfile', 'xdr-polling', 'xhr-polling',
        'iframe-xhr-polling', 'jsonp-polling'];

var stompConnErrorHandler = function(error) {
    alert("STOMP CONN ERROR HANDLER: WebSocket failed to connect. " + error);
};

/**
 * 如果没有连接，则返回null，否则订阅并返回订阅对象。
 * 
 * @param topicUrl topic URL，形如'/topic/greetings'
 * @param resultHandler 结果处理函数，该函数接受一个参数，这个参数是字符串表示的JSON对象
 * @returns 订阅对象
 */
function subscribeStomp(topicUrl, resultHandler) {

    if (STOMP_CLIENT) {
        var subscription = STOMP_CLIENT.subscribe(topicUrl, resultHandler);
        return subscription;
    }

    return null;
}

/**
 * 发送WebSocket广播消息。
 * 
 * @param url 广播目的地，形如'/app/greetings'
 * @param obj javascript对象
 */
function sendStomp(url, obj) {
    if (STOMP_CLIENT) {
        STOMP_CLIENT.send(url, {}, JSON.stringify(obj));
    }
}

/**
 * 连接并订阅WebSocket消息，订阅之后无法取消订阅。A.与disconnectStomp一起使用时，用于用户交互过程中的唯一一个一次性的服务端消息(画面上不能同时使用其他消息)。
 * B.单独使用时，用于画面开启后就订阅的一个或多个持续性的服务端消息。A和B均无法执行unsubscribe()。
 * 
 * @param endpointUrl STOMP end point URL，形如'/websocket/stomp'
 * @param onConnectedHandler 连接成功的回调函数
 * @param topicUrlArray 场合A时，表示topic URL字符串。场合B时，表示topic URL数组，形如'/topic/greetings'
 * @param resultHandlerArray 结果处理函数数组，该函数接受一个参数，这个参数是字符串表示的JSON对象
 */
function connectSubscribeStomp(endpointUrl, onConnectedHandler, topicUrlArray, resultHandlerArray) {
    SOCKET = new SockJS(BASE_URL + endpointUrl, undefined, { // BASE_URL在commonJsVar.jsp中定义
        // protocols_whitelist: TRANSPORTS_NOWEBSOCKET // 0.3.4的sockjs.js中使用，新版应使用transports
        transports: NEW_TRANSPORTS_NOWEBSOCKET
    });
    STOMP_CLIENT = Stomp.over(SOCKET);
    STOMP_CLIENT.connect({}, function(frame) {
        if ($.isArray(topicUrlArray)) {
            for (var i = 0; i < topicUrlArray.length; i++) {
                STOMP_CLIENT.subscribe(topicUrlArray[i], resultHandlerArray[i]);
            }
        } else {
            STOMP_CLIENT.subscribe(topicUrlArray, resultHandlerArray);
        }
        if ($.isFunction(onConnectedHandler)) {
            // 因为subscribe方法是异步的，且有一定耗时，使用setTimeout确保onConnectedHandler在subscribe之后执行，
            // 以免尚未完成订阅，后台就开始推送消息了。
            setTimeout(onConnectedHandler, 200);
        }
    }, stompConnErrorHandler);
}

/**
 * 断开WebSocket连接。
 */
function disconnectStomp() {
    if (STOMP_CLIENT) {
        STOMP_CLIENT.disconnect();
        STOMP_CLIENT = null;
    }
}