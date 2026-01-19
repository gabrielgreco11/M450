import { useEffect, useState } from "react";
import { updateRecipe } from "../../apis/recipesApi";
import { useParams } from "react-router-dom";

function EditRecipe() {
  const { id } = useParams();

  const [recipe, setRecipe] = useState({
    name: "",
    description: "",
    imageUrl: ""
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`http://localhost:8080/api/recipes/recipe/${id}`)
      .then(res => res.json())
      .then(data => {
        setRecipe(data);
        setLoading(false);
      });
  }, [id]);

  const handleChange = (e) => {
    setRecipe({
      ...recipe,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await updateRecipe(id, recipe);
    alert("Recipe updated!");
  };

  if (loading) return <p>Loading...</p>;

  return (
    <form onSubmit={handleSubmit}>
      <h2>Edit Recipe</h2>

      <input
        name="name"
        value={recipe.name}
        onChange={handleChange}
        placeholder="Name"
      />

      <textarea
        name="description"
        value={recipe.description}
        onChange={handleChange}
        placeholder="Description"
      />

      <input
        name="imageUrl"
        value={recipe.imageUrl}
        onChange={handleChange}
        placeholder="Image URL"
      />

      <button type="submit">Save</button>
    </form>
  );
}

export default EditRecipe;
