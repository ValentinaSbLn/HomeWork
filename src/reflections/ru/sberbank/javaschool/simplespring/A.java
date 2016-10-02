package reflections.ru.sberbank.javaschool.simplespring;

import reflections.ru.sberbank.javaschool.simplespring.x.CImlp;

/**
 * Created by Valentina on 02.10.2016.
 */
@Component
public class A { //TODO: не абстрактный и не интерфейс


    @Autowired
    private CImlp d;

    @Autowired
    private B b;

    @PostConstruct
    public void init() {
        System.out.println("init");
        //TODO: some logic
    }

    public void execute() {
        System.out.println(b.getSomeData());
        System.out.println(d.getSomeStr());
    }

    @PreDestroy
    public void destroy() {
        //TODO: some logic
        System.out.println("destroy a");
    }

}

