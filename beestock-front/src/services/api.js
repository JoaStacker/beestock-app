import { useEffect, useState } from 'react';

const fetchData = async (url) => {
  const response = await fetch(url);
  if (!response.ok) throw new Error('Network response was not ok');
  return response.json();
};

export const useApi = (url) => {
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const result = await fetchData("http://localhost:8000/"+url);
        setData(result);
      } catch (err) {
        setError(err);
      }
    };

    fetchDataAsync();
  }, [url]);

  return { data, error };
};
