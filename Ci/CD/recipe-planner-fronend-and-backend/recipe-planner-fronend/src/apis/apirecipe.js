export async function updateRecipe(id, recipe) {
  const response = await fetch(`http://localhost:8080/api/recipes/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(recipe)
  });

  if (!response.ok) {
    throw new Error("Failed to update recipe");
  }

  return response.json();
}
