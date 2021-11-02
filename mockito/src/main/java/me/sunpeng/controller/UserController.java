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
@RequestMapping("/user")
@Api(tags = "用户信息管理")
public class UserController {



    @RequestMapping(method = RequestMethod.POST,value = "/userById")
    @ApiOperation(value = "获取用户信息", notes = "通过用户ID获取用户信息")
    public Object findById(@ApiParam(value = "用户ID",required = true) int id){
        return "hello";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/userByName")
    @ApiOperation(value = "获取用户信息", notes = "通过用户姓名获取用户信息")
    public Object findByName(@ApiParam(value = "用户姓名",required = true) String  name){
        return "hello";
    }


    @ApiOperation(value="用户登录",notes="随边说点啥")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mobile",value="手机号",required=true,paramType = "form"),
            @ApiImplicitParam(name="password",value="密码",required=true,paramType = "form")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String mobile, @RequestParam String password){
        System.out.println(mobile);
        System.out.println(password);
        final User user = new User();
        user.setPassword(password);
        user.setMobile(mobile);
        user.setName("test");
        user.setId(1);

        return new ResponseEntity(user,HttpStatus.OK);
    }

    @ApiOperation(value = "创建用户信息",notes = "创建用户信息")
//    @ApiImplicitParam(name = "user",value = "user",required = true,paramType = "body")
    @PostMapping("/add/user")
    public ResponseEntity login(@RequestBody User user){

        System.out.println(user);
        return new ResponseEntity(user,HttpStatus.OK);
    }

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId", dataType="String", required=true, value="用户Id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @ResponseBody
    @RequestMapping("/list")
    public ResponseEntity list(@RequestParam String userId) {

        System.out.println("userId: "+ userId);
        return new ResponseEntity(userId,HttpStatus.OK);
    }

    }



