package com.loneliess.servise;

import com.loneliess.entity.Cone;
import com.loneliess.repository.RepositoryConeRegistrar;
import com.loneliess.repository.RepositoryFactory;
import com.loneliess.subscriber.ConeRegistrar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.concurrent.SubmissionPublisher;

public class ConeWrapper {
    private SubmissionPublisher<Cone> publisher = new SubmissionPublisher<>();
    private ConeRegistrar<Cone> subscriber=new ConeRegistrar<>();
    private Cone cone=new Cone();
    private RepositoryConeRegistrar repository=RepositoryFactory.getInstance().getRepository();
    public ConeWrapper(int id,
                       @NotNull(message = "Обязательно должна быть задана длина образующей конуса")
                       @Positive(message = "Длина образующей конуса должна быть положительна. ") double l,
                       @NotNull(message = "Обязательно должна быть задана длина радиуса окружности")
                       @Positive(message = "Длина радиуса окружности основания должна быть положительна. ")
                               double r, @NotNull(message = "Обязательно должна быть задана высота конуса")
                       @Positive(message = "Высота конуса должна быть положительна. ") double h,
                       double x1,double y1,double z1,double x2,double y2,double z2){
        publisher.subscribe(subscriber);
        cone.setAll(id,l,  r, h, x1, y1, z1, x2, y2, z2);
        publisher.submit(cone);
        repository.add(subscriber);
    }
    public ConeWrapper(){
        publisher.subscribe(subscriber);
        publisher.submit(cone);
        repository.add(subscriber);
    }

    public void addCone(Cone cone){
        this.cone=cone;
        publisher.submit(cone);
    }

    public void updateCone(Cone cone){
        this.cone=cone;
        publisher.submit(cone);


    }
    public void deleteCone(){
        repository.delete(subscriber);
        publisher.close();
        publisher=null;
        subscriber=null;
        cone=null;

    }
    public Cone getCone(){
        return cone;
    }

    public ConeRegistrar<Cone> getSubscriber() {
        return subscriber;
    }

}
