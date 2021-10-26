package me.sunpeng;

import me.sunpneg.StartAppliction9000;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sp
 * @date 2021-10-25 11:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartAppliction9000.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockitoTest {

    @Test
    public void Test01() {
        //模拟创建一个List对象
        List<Integer> mock = Mockito.mock(List.class);
        //调用mock对象的方法
        mock.add(1);
        mock.clear();
        //验证方法是否执行
        Mockito.verify(mock).add(1);
        Mockito.verify(mock).clear();
        Mockito.verify(mock).add(2);
    }

    @Test
    public void Test02() {
        LinkedList mockedList = Mockito.mock(LinkedList.class);
        Mockito.when(mockedList.get(0)).thenReturn("first");
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(999));
        Mockito.verify(mockedList, Mockito.atLeast(2)).get(0);
    }

    @Test(expected = IOException.class)//期望报io异常
    public void Test03() throws IOException {
        OutputStream mock = Mockito.mock(OutputStream.class);
        //预设当流关闭时候抛出异常
        Mockito.doThrow(new IOException()).when(mock).close();
        mock.close();
    }

    @Test
    public void deepstubsTest() {
        A a = Mockito.mock(A.class);
        B b = Mockito.mock(B.class);
        Mockito.when(a.getB()).thenReturn(b);
        Mockito.when(b.getName()).thenReturn("Beijing");
        Assert.assertEquals("Beijing", a.getB().getName());


    }

    @Test
    public void deepstubsTest2() {
        A a = Mockito.mock(A.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(a.getB().getName()).thenReturn("Beijing");
        Assert.assertEquals("Beijing", a.getB().getName());
    }

    @Test
    public void with_arguments() {
        B b = Mockito.mock(B.class);
        //预设根据不同的参数返回不同的结果
        Mockito.when(b.getSex(1)).thenReturn("男");
        Mockito.when(b.getSex(2)).thenReturn("女");
        Assert.assertEquals("男", b.getSex(1));
        Assert.assertEquals("女", b.getSex(2));
        //对于没有预设的情况会返回默认值
        Assert.assertEquals(null, b.getSex(0));
    }

    @Test
    public void with_upspecified_arguments() {
        List list = Mockito.mock(List.class);
        //匹配任意参数
        Mockito.when(list.get(Mockito.anyInt())).thenReturn(1);
        Mockito.when(list.contains(Mockito.argThat(new IsValid()))).thenReturn(true);

        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(1, list.get(999));

        Assert.assertTrue(list.contains(3));
        Assert.assertTrue(!list.contains(3));

    }

    class IsValid implements ArgumentMatcher<Object> {
        /*
         *参数匹配，如果是1或者2 返会true
         * */
        @Override
        public boolean matches(Object obj) {
            return obj.equals(1) || obj.equals(2);
        }
    }

    class A {
        private B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    class B {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex(Integer sex) {
            if (sex == 1) {
                return "man";
            } else {
                return "woman";
            }

        }

    }


}
