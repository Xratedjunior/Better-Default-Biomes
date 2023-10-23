package xratedjunior.betterdefaultbiomes.entity.client.model.util;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public class BDBModelPart {
	public ModelPart modelPart;
	public float defaultRotationPointX, defaultRotationPointY, defaultRotationPointZ;
	public float defaultRotateAngleX, defaultRotateAngleY, defaultRotateAngleZ;

	public BDBModelPart(BDBEntityModel<? extends Entity> entityModel, ModelPart modelPart) {
		this.modelPart = modelPart;
		entityModel.addModelPart(this);
	}

	/*********************************************************** ModelParts ********************************************************/

	public ModelPart getModelPart() {
		return this.modelPart;
	}

	/*********************************************************** Defaults ********************************************************/

	/**
	 * A method that sets the default box's values
	 */
	public void setDefaultModelValues() {
		this.defaultRotationPointX = this.getModelPart().x;
		this.defaultRotationPointY = this.getModelPart().y;
		this.defaultRotationPointZ = this.getModelPart().z;

		this.defaultRotateAngleX = this.getModelPart().xRot;
		this.defaultRotateAngleY = this.getModelPart().yRot;
		this.defaultRotateAngleZ = this.getModelPart().zRot;
	}

	/**
	 * A method that reverts the current box's values back to the default values
	 */
	public void getDefaultModelValues() {
		this.getModelPart().x = this.defaultRotationPointX;
		this.getModelPart().y = this.defaultRotationPointY;
		this.getModelPart().z = this.defaultRotationPointZ;

		this.getModelPart().xRot = this.defaultRotateAngleX;
		this.getModelPart().yRot = this.defaultRotateAngleY;
		this.getModelPart().zRot = this.defaultRotateAngleZ;
	}
}
