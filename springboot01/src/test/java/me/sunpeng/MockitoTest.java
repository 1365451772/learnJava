package me.sunpeng;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

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
        List<Integer> mock = mock(List.class);
        //调用mock对象的方法
        mock.add(1);
        mock.clear();
        //验证方法是否执行
        verify(mock).add(1);
        verify(mock).clear();
        verify(mock).add(2);
    }

    @Test
    public void Test02() {
        LinkedList mockedList = mock(LinkedList.class);
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(999));
        verify(mockedList, atLeast(2)).get(0);
    }

    @Test(expected = IOException.class)//期望报io异常
    public void Test03() throws IOException {
        OutputStream mock = mock(OutputStream.class);
        //预设当流关闭时候抛出异常
        doThrow(new IOException()).when(mock).close();
        mock.close();
    }

    @Test
    public void deepstubsTest() {
        A a = mock(A.class);
        B b = mock(B.class);
        when(a.getB()).thenReturn(b);
        when(b.getName()).thenReturn("Beijing");
        Assert.assertEquals("Beijing", a.getB().getName());


    }

    @Test
    public void deepstubsTest2() {
        A a = mock(A.class, RETURNS_DEEP_STUBS);
        when(a.getB().getName()).thenReturn("Beijing");
        Assert.assertEquals("Beijing", a.getB().getName());
    }

    @Test
    public void with_arguments() {
        B b = mock(B.class);
        //预设根据不同的参数返回不同的结果
        when(b.getSex(1)).thenReturn("男");
        when(b.getSex(2)).thenReturn("女");
        Assert.assertEquals("男", b.getSex(1));
        Assert.assertEquals("女", b.getSex(2));
        //对于没有预设的情况会返回默认值
        Assert.assertEquals(null, b.getSex(0));
    }

    @Test
    public void with_upspecified_arguments() {
        List list = mock(List.class);
        //匹配任意参数
        when(list.get(anyInt())).thenReturn(1);
        when(list.contains(argThat(new IsValid()))).thenReturn(true);

        Assert.assertEquals(1, list.get(1));
        Assert.assertEquals(1, list.get(999));

        Assert.assertTrue(list.contains(3));
        Assert.assertTrue(!list.contains(3));

    }

    @Test
    public void with_upspecified_arguments2() {
        Comparator comparator = mock(Comparator.class);
        comparator.compare("nihao", "h");
        //如果你使用了参数匹配，那么所有参数都必须通过matchers来匹配
        //第一项anyString代表任何字符串，第二项 eq("h") 所以compare(*,"h")
        verify(comparator).compare(anyString(), eq("h"));
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
