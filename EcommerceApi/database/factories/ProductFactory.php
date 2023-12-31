<?php

namespace Database\Factories;

use App\Models\Product;
use Illuminate\Database\Eloquent\Factories\Factory;

class ProductFactory extends Factory
{
    /**
     * The name of the factory's corresponding model.
     *
     * @var string
     */
    protected $model = Product::class;

    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition()
    {
        return [
            'name' => $this->faker->text(20),
            'description' => $this->faker->realText(50,2),
            'amount'=>$this->faker->numberBetween(20,100),
            'price' => $this->faker->numberBetween(100,300), 
            'image' => 'b.png',
            'category_id'=>$this->faker->numberBetween(1,3)
        ];
    }
}
