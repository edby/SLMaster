package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.authorization.annotation.*;
import com.yibi.orderapi.biz.OrderV2Biz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 现货、杠杆交易
 */
@Controller
@RequestMapping("/order/v2")
public class OrderV2Controller extends BaseController {
    @Autowired
    private OrderV2Biz orderV2Biz;
    /**
     * 限价买入
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/limitPriceBuy/v2", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String limitPriceBuy(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String price = json.getString("price");
            String amount = json.getString("amount");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(price) || StrUtils.isBlank(amount) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderV2Biz.limitPriceBuy(user, orderCoin, unitCoin, levFlag, price, amount, password);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }  catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 限价卖出
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/limitPriceSale/v2", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String limitPriceSale(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String price = json.getString("price");
            String amount = json.getString("amount");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(price) || StrUtils.isBlank(amount) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderV2Biz.limitPriceSale(user, orderCoin, unitCoin, levFlag, price, amount, password);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }  catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }


    /**
     * 市价买入
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/marketPriceBuy/v2", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String marketPriceBuy(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String total = json.getString("total");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(total) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderV2Biz.marketPriceBuy(user, orderCoin, unitCoin, levFlag, total, password);
        }catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }  catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 市价卖出
     * @param user
     * @param params
     * @return
     */
    @Decrypt
    @Authorization
    @RequestMapping(value = "/marketPriceSale/v2", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String marketPriceSale(@CurrentUser User user, @Params Object params) {
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer orderCoin = json.getInteger("orderCoin");
            Integer unitCoin = json.getInteger("unitCoin");
            Integer levFlag = json.getInteger("levFlag");
            String amount = json.getString("amount");
            String password = json.getString("password");
            /*参数校验*/
            if (StrUtils.isBlank(amount) || StrUtils.isBlank(password) || orderCoin == null || unitCoin == null) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            return orderV2Biz.marketPriceSale(user, orderCoin, unitCoin, levFlag, amount, password);
        } catch (BanlanceNotEnoughException e){
            e.printStackTrace();
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
