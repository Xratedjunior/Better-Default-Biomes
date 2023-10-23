package xratedjunior.betterdefaultbiomes.entity.client.model.util;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author  Xrated_junior
 * @version 1.18.2-Alpha 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public abstract class BDBEntityModel<E extends Entity> extends EntityModel<E> {
	protected List<BDBModelPart> modelParts = Lists.newArrayList();

	public BDBEntityModel(Function<ResourceLocation, RenderType> renderType) {
		super(renderType);
	}

	/*********************************************************** Model Parts ********************************************************/

	protected BDBModelPart createModelPart(EntityModel<E> entityModel, ModelPart root, String partName) {
		return new BDBModelPart(this, root.getChild(partName));
	}

	protected BDBModelPart createModelPart(EntityModel<E> entityModel, BDBModelPart root, String partName) {
		return this.createModelPart(entityModel, root.getModelPart(), partName);
	}

	/*********************************************************** Model Defaults ********************************************************/

	public void addModelPart(BDBModelPart modelPart) {
		this.modelParts.add(modelPart);
	}

	public void setDefaultModelValues() {
		this.modelParts.forEach((rendererModel) -> {
			if (rendererModel instanceof BDBModelPart) {
				((BDBModelPart) rendererModel).setDefaultModelValues();
			}
		});
	}

	public void revertToDefaultModelValues() {
		this.modelParts.forEach((rendererModel) -> {
			if (rendererModel instanceof BDBModelPart) {
				((BDBModelPart) rendererModel).getDefaultModelValues();
			}
		});
	}

	/*********************************************************** Animation ********************************************************/

	/*
	 * Checks if the entity is moving
	 */
	protected boolean isWalking(E entity) {
		return entity.xo != entity.getX() || entity.yo != entity.getY() || entity.zo != entity.getZ();
	}

	public void setRotationAngle(ModelPart modelPart, float xRot, float yRot, float zRot) {
		modelPart.xRot = xRot;
		modelPart.yRot = yRot;
		modelPart.zRot = zRot;
	}

	/*********************************************************** Looped Animation Tools ********************************************************/

	protected float calculateRotationAngle(float speed, float degree, boolean invert, float delay, float limbSwing, float limbSwingAmount) {
		if (invert == true) {
			float rotation = (Mth.cos(limbSwing * speed + ((float) Math.PI + delay)) * degree * limbSwingAmount);
			return rotation;
		} else {
			float rotation = (Mth.cos(limbSwing * speed + delay) * degree * limbSwingAmount);
			return rotation;
		}
	}

	/**
	 * Rotates this back and forth (rotateAngleX)
	 */
	protected void rotateX(BDBModelPart modelPart, float speed, float degree, boolean invert, float delay, float limbSwing, float limbSwingAmount) {
		modelPart.getModelPart().xRot += this.calculateRotationAngle(speed, degree, invert, delay, limbSwing, limbSwingAmount);
	}

	/**
	 * Rotates this back and forth (rotateAngleX), but only in the positive direction
	 */
	protected void rotateXPositive(BDBModelPart modelPart, float speed, float degree, boolean invert, float delay, float limbSwing, float limbSwingAmount) {
		modelPart.getModelPart().xRot += Mth.abs(this.calculateRotationAngle(speed, degree, invert, delay, limbSwing, limbSwingAmount));
	}

	/**
	 * Rotates this box right and left (rotateAngleZ)
	 */
	public void rotateZ(BDBModelPart modelPart, float speed, float degree, boolean invert, float delay, float limbSwing, float limbSwingAmount) {
		modelPart.getModelPart().zRot += this.calculateRotationAngle(speed, degree, invert, delay, limbSwing, limbSwingAmount);
	}

	/**
	 * Rotates this box side to side (rotateAngleY)
	 */
	public void shake(BDBModelPart modelPart, float speed, float degree, boolean invert, float delay, float weight, float limbSwing, float limbSwingAmount) {
		modelPart.getModelPart().yRot += this.calculateRotationAngle(speed, degree, invert, delay, limbSwing, limbSwingAmount);
	}

	/**
	 * This makes the given Box move Up and Down on the Y Axis
	 */
	protected void bounce(BDBModelPart modelPart, float speed, float height, float delay, float limbSwing, float limbSwingAmount) {
		float bounce = (float) (Math.sin(limbSwing * speed + delay) * limbSwingAmount * height);
		modelPart.getModelPart().y += Mth.abs(bounce);
	}
}