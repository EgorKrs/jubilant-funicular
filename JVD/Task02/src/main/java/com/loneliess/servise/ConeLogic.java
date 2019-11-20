package com.loneliess.servise;

import com.loneliess.UniqueID;
import com.loneliess.entity.Cone;
import com.loneliess.entity.Point;
import com.loneliess.repository.RepositoryException;
import com.loneliess.repository.RepositoryFactory;
import com.loneliess.resource_provider.LogManager;
import org.apache.logging.log4j.Level;

public class ConeLogic {
    private static final ConeLogic instance = new ConeLogic();

    private ConeLogic() {
    }

    public static ConeLogic getInstance() {
        return instance;
    }

    public double calculateSideSurfaceArea(Cone cone) {
        return cone.getR() * cone.getL() * Math.PI;
    }

    public double calculateConeSurfaceArea(Cone cone) {
        return Math.PI * cone.getR() * (cone.getR() + cone.getL());
    }

    public double calculateVolume(Cone cone) {
        return 1.0 / 3.0 * Math.PI * Math.pow(cone.getR(), 2) * cone.getH();
    }

    public double CalculateVolumeRatio(Cone cone, Point point) {
        Point coordinate = PointLogic.getInstance().difference(cone.getCoordinateTheCenterOfCircle(), point);
        double coefficient = cone.getCoordinateTheCenterOfCircle().getCoordinateY() - coordinate.getCoordinateY();
        Cone cone1 = new Cone(UniqueID.getInstance().getId(),
                cone.getL() * (Math.pow(Math.pow(cone.getR(), 2) + Math.pow(cone.getH(), 2), 0.5)),
                cone.getR() * (coefficient), cone.getH() * (coefficient));
        return Math.abs(calculateVolume(cone) - calculateVolume(cone1));
    }

    public boolean isLiesOnThePlane(Cone cone) {
        if (cone.getCoordinateTheCenterOfCircle().getCoordinateY() == 0 && cone.getCoordinateBorderLineCircle().getCoordinateY() == 0) {
            return true;
        } else if (cone.getCoordinateTheCenterOfCircle().getCoordinateX() == 0 && cone.getCoordinateBorderLineCircle().getCoordinateX() == 0) {
            return true;
        } else
            return cone.getCoordinateTheCenterOfCircle().getCoordinateZ() == 0 && cone.getCoordinateBorderLineCircle().getCoordinateZ() == 0;
    }

    public String splitToCoordinate(Cone cone){
        return cone.getId() + " " +
                cone.getL() + " " +
                cone.getR() + " " +
                cone.getH() + " " +
                cone.getCoordinateTheCenterOfCircle().getCoordinateX() + " " +
                cone.getCoordinateTheCenterOfCircle().getCoordinateY() + " " +
                cone.getCoordinateTheCenterOfCircle().getCoordinateZ() + " " +
                cone.getCoordinateBorderLineCircle().getCoordinateZ() + " " +
                cone.getCoordinateBorderLineCircle().getCoordinateZ() + " " +
                cone.getCoordinateBorderLineCircle().getCoordinateZ() + " ";
    }

    public boolean isEquals(Cone cone1, Cone cone) {
        return cone.equals(cone1);
    }

    public boolean addCone(double l, double r,  double h,
                           double x1,double y1,double z1,double x2,double y2,double z2){
        Cone cone=new Cone(UniqueID.getInstance().getId(),l,  r,   h, x1, y1, z1, x2, y2, z2);
        if(ServiceFactory.getInstance().getServiceValidation().validate(cone).size()==0){
            RepositoryFactory.getInstance().getRepositoryCone().getData().put(cone.getId(),cone);
            return true;
        }
        else return false;
    }
    public boolean addCone(Cone cone){
        if(ServiceFactory.getInstance().getServiceValidation().validate(cone).size()==0){
            RepositoryFactory.getInstance().getRepositoryCone().getData().put(cone.getId(),cone);
            return true;
        }
        else return false;
    }
    public boolean updateCone(Cone cone) throws ServiceException {
        if(ServiceFactory.getInstance().getServiceValidation().validate(cone).size()==0){
            try {
                return RepositoryFactory.getInstance().getRepositoryCone().update(cone);
            } catch (RepositoryException e) {
                LogManager.getInstance().getLogger().catching(Level.ERROR,e);
                throw new ServiceException(e,"Ошибка обговления в updateCone(Cone cone) "+e);
            }
        }
        else return false;
    }
    public boolean deleteCone(Cone cone) throws ServiceException {
        if (RepositoryFactory.getInstance().getRepositoryCone().getData().containsKey(cone.getId())){
            try {
                return RepositoryFactory.getInstance().getRepositoryCone().delete(cone);
            } catch (RepositoryException e) {
                LogManager.getInstance().getLogger().catching(Level.ERROR,e);
                throw new ServiceException(e,"Ошибка обговления в  deleteCone(Cone cone) "+e);
            }
        }
        else return false;
    }

    public boolean saveConeMap() throws ServiceException {
        try {
            return RepositoryFactory.getInstance().getRepositoryCone().save(RepositoryFactory.getInstance().getRepositoryCone().getData());
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().catching(Level.ERROR,e);
            throw new ServiceException(e,"Ошибка сохранения данных в saveConeMap() "+e);
        }
    }
}
