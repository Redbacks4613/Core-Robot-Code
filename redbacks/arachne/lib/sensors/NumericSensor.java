package redbacks.arachne.lib.sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import redbacks.arachne.lib.logic.GettableNumber;

/**
 * A superclass for all sensors returning a numeric reading.
 *
 * @author Sean Zammit
 */
public abstract class NumericSensor implements GettableNumber, PIDSource
{
	protected PIDSourceType pidType;

	public double offset = 0, pauseValue = 0, scaleFactor = 1;
	public boolean isPaused = false;

	public final double get() {
		if(isPaused) return pauseValue * scaleFactor;
		return (getSenVal() + offset) * scaleFactor;
	}

	/**
	 * Overridden by individual sensor classes to specify their reading.
	 * 
	 * @return The sensor value to be used by NumericSensor.
	 */
	protected abstract double getSenVal();

	/**
	 * Sets the value of the sensor.
	 * Any change in reading will then be from this point.
	 * 
	 * @param value The value to set the sensor to.
	 */
	public void set(double value) {
		offset = value - getSenVal();
	}

	/**
	 * Pauses the reading from the sensor.
	 * The reading will remain constant while paused.
	 */
	public final void pause() {
		pauseValue = getSenVal() + offset;
		isPaused = true;
	}

	/**
	 * Unpauses the reading from the sensor.
	 * Any change in reading will then be from the paused value.
	 */
	public final void unpause() {
		isPaused = false;
		set(pauseValue);
	}

	/**
	 * Sets the multiplier used when getting sensor readings.
	 * 
	 * @param scale The multiplier to use.
	 */
	public final void setScaleFactor(double scale) {
		this.scaleFactor = scale;
	}

	public final void setPIDSourceType(PIDSourceType pidType) {
		this.pidType = pidType;
	}

	public final PIDSourceType getPIDSourceType() {
		return pidType;
	}

	public double pidGet() {
		return get();
	}
}
