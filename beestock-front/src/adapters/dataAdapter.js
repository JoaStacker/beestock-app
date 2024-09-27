export const adaptData = (data) => {
    // Transform data as needed
    return data.map(item => ({
      id: item.id,
      name: item.name,
      // More transformations if needed
    }));
  };
  