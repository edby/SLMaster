package com.yibi.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.yibi.websocket.biz.*;
import com.yibi.websocket.model.Result;
import com.yibi.websocket.model.ResultCode;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends
        SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private PingBiz pingBiz;
    @Resource(name = "baseBizImpl")
    private BaseBiz baseBiz;
    @Autowired
    private JoinBiz joinBiz;
    @Autowired
    private Join2Biz join2Biz;
    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private BroadCastBiz broadCastBiz;

    public static ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    public static Map<String, WebSocketClient> allSocketClients = new ConcurrentHashMap<>();


    /**
     * 客户端发来消息时触发
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        Channel incoming = ctx.channel();
        String reciveMsg = msg.text();
//        log.info("websocket服务器收到数据：" + reciveMsg);
        try {
            JSONObject json = JSONObject.parseObject(reciveMsg);
            String action = json.getString("action");
            String cmsgCode = json.getString("cmsg_code");
            if (cmsgCode == null || "".equals(cmsgCode)) {
                cmsgCode = "-1";
            }
            ResultObj resultObj = new ResultObj();
            resultObj.setCmsgCode(cmsgCode);
            switch (action) {
                case "ping": {
                    pingBiz.ping(incoming, cmsgCode);
                    break;
                }
                case "join": {
                    JSONObject data = json.getJSONObject("data");
                    if (data == null) {
                        resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
                        resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
                        baseBiz.sendMessage(incoming, resultObj);
                        return;
                    }
                    joinBiz.join(incoming, data, resultObj, allSocketClients);

                    break;
                }
                case "join2": {
                    JSONObject data = json.getJSONObject("data");
                    if (data == null) {
                        resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
                        resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
                        baseBiz.sendMessage(incoming, resultObj);
                        return;
                    }
                    join2Biz.join(incoming, data, resultObj, allSocketClients);

                    break;
                }
                case "order": {
                    JSONObject data = json.getJSONObject("data");
                    if (data != null) {
                        orderBiz.orderBroadcast(data, allSocketClients);
                        return;
                    }

                    break;
                }
                case "broadcast": {
                    JSONObject data = json.getJSONObject("data");
                    if (action == null || "".equals(action) || data == null) {
                        resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
                        resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
                        baseBiz.sendMessage(incoming, resultObj);
                        return;
                    }
                    broadCastBiz.broadCast(data, allSocketClients);
                    break;
                }
                default: {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            ResultObj resultObj = new ResultObj();
            resultObj.setMsg(ResultCode.TYPE_ERROR_UNKNOW.message());
            resultObj.setCode(ResultCode.TYPE_ERROR_UNKNOW.code());
            //baseBiz.sendMessage(incoming, resultObj);
            // ctx.close();
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
        Channel incoming = ctx.channel();
        group.add(incoming);
        incoming.writeAndFlush(new TextWebSocketFrame(""));
        log.info("Client:" + incoming.remoteAddress().toString() + "加入,当前链接数："
                + group.size());
		/*WebSocketClient sendClient = new WebSocketClient(incoming.remoteAddress().toString());
		allSocketClients.put(incoming.remoteAddress().toString(),sendClient);
		
		logger.info("当前所有用户的remoteAddress：");
		for (String key : allSocketClients.keySet()) {
			logger.info(key);
		}*/
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
        Channel incoming = ctx.channel();
		/*group.writeAndFlush(new TextWebSocketFrame("[SERVER] - "
				+ incoming.remoteAddress() + " 离开"));*/
        log.info("Client:" + incoming.remoteAddress().toString() + "离开,当前链接数："
                + group.size());
        allSocketClients.remove(incoming.remoteAddress().toString());
        incoming.close();
        // Broadcast a message to multiple Channels
        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) // (7)
            throws Exception {
        Channel incoming = ctx.channel();
        log.info("Client:" + incoming.remoteAddress() + "异常");
        allSocketClients.remove(incoming.remoteAddress().toString());
        cause.printStackTrace();
        //sendMessage(incoming, Result.toResultStr(ResultCode.TYPE_ERROR_UNKNOW));
    }


}