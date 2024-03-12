package hello.servlet.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // Repository 를 singleton 으로 만들기
    // Spring 을 쓰면 모든 bean(객체)는 singleton 으로 관리되므로 이렇게 안해줘도 되는데
    // 지금은 spring 을 쓰는게 아니므로 강제로 singleton 으로 만들어주어야한다
    private static final MemberRepository instance = new MemberRepository();

    // singleton 이므로 무조건 instanc 로 참조해야함!
    public static MemberRepository getInstance(){
        return instance;
    }

    // singleton 이므로 private 생성자로 외부 생성 막아주기
    private MemberRepository(){

    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
