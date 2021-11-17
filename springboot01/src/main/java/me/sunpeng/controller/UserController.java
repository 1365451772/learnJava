package me.sunpeng.controller;

import io.swagger.annotations.*;
import me.sunpeng.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author sp
 * @date 2021-11-01 20:20
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户信息管理")
public class UserController {


    @RequestMapping(method = RequestMethod.POST, value = "/userById")
    @ApiOperation(value = "获取用户信息", notes = "通过用户ID获取用户信息")
    public Object findById(@ApiParam(value = "用户ID", required = true) int id) {
        return "hello";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userByName")
    @ApiOperation(value = "获取用户信息", notes = "通过用户姓名获取用户信息")
    public Object findByName(@ApiParam(value = "用户姓名", required = true) String name) {
        return "hello";
    }


    @ApiOperation(value = "用户登录", notes = "随边说点啥")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form")
    })
    @PostMapping("/login")
    public ResponseEntity login(@ModelAttribute User user) {
        user.setName("test");
        user.setId(1);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @ApiOperation(value = "创建用户信息", notes = "创建用户信息")
//    @ApiImplicitParam(name = "user",value = "user",required = true,paramType = "body")
    @PostMapping("/add/user")
    public ResponseEntity addUser(@RequestBody User user) {

        System.out.println(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "String", required = true, value = "用户Id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @ResponseBody
    @RequestMapping("/list")
    public ResponseEntity list(@RequestParam String userId) {

        System.out.println("userId: " + userId);
        return new ResponseEntity(userId, HttpStatus.OK);
    }


    @PostMapping(value = "/{id}/secondPage", headers = "content-type=multipart/form-data")
    @ApiOperation(httpMethod = "POST", value = "字段1，字段2，字段3/文件导入(包括增加/更新)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "excel文件", required = false, paramType = "form", dataType = "__file"),
            @ApiImplicitParam(name = "channelList", value = "字段1", required = true, paramType = "form", allowMultiple = true, dataType = "string"),
            @ApiImplicitParam(name = "organList", value = "字段2", required = true, paramType = "form", allowMultiple = true, dataType = "string"),
            @ApiImplicitParam(name = "accountList", value = "字段3", required = false, paramType = "form", allowMultiple = true, dataType = "string"),
            @ApiImplicitParam(name = "organIdList", value = "字段4", required = false, paramType = "form", allowMultiple = true, dataType = "string")
    })
    public void createLearningPlanSecond(@ApiParam(name = "id", value = "学习计划ID", required = true) @PathVariable(required = true) String id,
                                         @ApiParam(name = "format", value = "字段1，字段2，字段3/文件导入(包括增加/更新)", required = true) @RequestParam(required = true) Integer format ){
    }
}







