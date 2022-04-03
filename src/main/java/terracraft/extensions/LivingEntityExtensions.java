package terracraft.extensions;

public interface LivingEntityExtensions {

	/**
	 * @param speed The original speed
	 * @return The swim speed after it was handled by terracraft
	 */
	double terracraft$getIncreasedSwimSpeed(double speed);

	/**
	 * Makes the LivingEntity double jump
	 * A double jump behaves slightly different from a normal jump (velocity, sprinting multiplier, particles & sound)
	 */
	void terracraft$doubleJump();
}
